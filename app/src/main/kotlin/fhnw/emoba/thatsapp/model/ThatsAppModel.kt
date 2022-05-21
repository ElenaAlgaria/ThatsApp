package fhnw.emoba.thatsapp.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.R
import fhnw.emoba.modules.module07.flutter_solution.data.MqttConnector
import fhnw.emoba.modules.module09.gps.data.GeoPosition
import fhnw.emoba.thatsapp.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ThatsAppModel(private val context: ComponentActivity) {
    var title      = ""
    val mqttBroker = "broker.hivemq.com"
    val mainTopic  = "fhnw/emoba/thatsapp"
    val allFlaps = mutableStateListOf<Flap>()

    var notificationMessage by mutableStateOf("")
    var message             by mutableStateOf("")
    var imageURL             by mutableStateOf("")
    var location             by mutableStateOf(GeoPosition())
    var gps by mutableStateOf(Gps())
    var me         by  mutableStateOf("Elena")
    var greeting         by  mutableStateOf("The world needs more love")

    var loc = false
    var currentScreen by mutableStateOf(AvailableScreen.OVERVIEW)
    val imagePic   = loadImage(R.drawable.character)

    private val mqttConnector by lazy { MqttConnector(mqttBroker) }
    private val soundPlayer   by lazy { MediaPlayer.create(context, R.raw.exp) }

    var chatList = mutableStateListOf<People>()

    val phrases = mutableStateListOf<String>()
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var currentPerson = People("", "", "", null)

    var uploadInProgress   by mutableStateOf(false)

    var downloadedImg     by mutableStateOf<Bitmap?>(null)
    var downloadInProgress by mutableStateOf(false)
    var downloadMessage    by mutableStateOf("")


    fun nextPhrase() {
        modelScope.launch {
            phrases.add(Fact.generateMsg())
        }
    }

    fun handlePeople(){
        chatList.add(People("Lio",message, mainTopic + "/Lio",loadImage(R.drawable.leo)))
        chatList.add(People("Milena", message, mainTopic + "/Milena",loadImage(R.drawable.milena)))
        chatList.add(People("Martin", message, mainTopic + "/Martin",loadImage(R.drawable.martin)))
        chatList.add(People("Lea", message, mainTopic + "/Lea",loadImage(R.drawable.lea)))

    }

    fun connectAndSubscribe(){
            mqttConnector.connectAndSubscribe(
                topic = mainTopic +"/" + me,
                onNewMessage = {
                    checkMsg(Flap(it))
                    allFlaps.add(Flap(it))
                    playSound()
                },
                onError = { _, p ->
                    notificationMessage = p
                    playSound()
                }
            )
        }

    fun publish(name: String){
        if (loc){
            message = ""
            loc = false
        }
        mqttConnector.publish(
            topic       = mainTopic + "/" + name,
            message     = Flap(sender  = me, receiver = name,
                message = message, imageUrl = imageURL, gps =  gps)
        )
        allFlaps.add(Flap(sender = me, receiver = name, message = message, imageUrl = imageURL, gps = gps))
    }

    fun uploadToFileIO(bitmap: Bitmap) {
        uploadInProgress = true
        //fileioURL = null
        modelScope.launch {
            uploadBitmapToFileIO(bitmap    = bitmap,
                onSuccess = { imageURL = it},
                onError   = {_, _ -> })  //todo: was machen wir denn nun?
            uploadInProgress = false
        }
    }

//allFlaps.add(Flap(it)) bi success
    fun downloadFromFileIO(flap: Flap){
        if(flap.imageUrl != null){
            downloadedImg = null
            downloadInProgress = true
            modelScope.launch {
                downloadBitmapFromFileIO(url       = flap.imageUrl!!,
                    onSuccess = { downloadedImg = it },
                    onDeleted = { downloadMessage = "File is deleted"},
                    onError   = { downloadMessage = "Connection failed"})
                downloadInProgress = false
            }
        }
    }

    //private fun loadImage(@DrawableRes id: Int) = BitmapFactory.decodeResource(activity.resources, id)

    private fun checkMsg(flap: Flap){
        if (flap.imageUrl != ""){
            downloadFromFileIO(flap)
        }
        if (flap.gps.latitude != "" && flap.gps.longitude != ""){
            location = gpsToGeo(flap.gps)
        }
    }

    fun geoToGps(geoPosition: GeoPosition): Gps {
        return Gps(geoPosition.longitude.toString(), geoPosition.latitude.toString())
    }

    fun gpsToGeo(gps: Gps): GeoPosition {
        return GeoPosition(gps.longitude.toDouble(), gps.latitude.toDouble())
    }

    fun messagesWithCurrentPerson(name: String): List<Flap> =
        allFlaps.filter { it.sender == name || it.receiver == name }

    private fun playSound(){
        soundPlayer.seekTo(0)
        soundPlayer.start()
    }

    fun loadImage(@DrawableRes id: Int) : ImageBitmap {
        return BitmapFactory.decodeResource(context.resources, id).asImageBitmap()
    }
}