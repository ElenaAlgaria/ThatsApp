package fhnw.emoba.thatsapp.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
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
import fhnw.emoba.thatsapp.data.Fact_2.APIFact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ThatsAppModel(private val context: ComponentActivity, val ser: APIFact) {
    var title = ""
    val mqttBroker = "broker.hivemq.com"
    val mainTopic = "fhnw/emoba/thatsapp"
    val allFlaps = mutableStateListOf<Flap>()

    var notificationMessage by mutableStateOf("")
    var message by mutableStateOf("")
    var imageURL by mutableStateOf("")
    var location by mutableStateOf(GeoPosition())
    var gps by mutableStateOf(Gps())
    var me by mutableStateOf("")
    var newPerson by mutableStateOf("")

    var greeting by mutableStateOf("The world needs more love")

    var currentScreen by mutableStateOf(AvailableScreen.PROFIL)
    val imagePic = loadImage(R.drawable.profil)

     val mqttConnector by lazy { MqttConnector(mqttBroker) }

    var chatList = mutableStateListOf<People>()

    val phrases = mutableStateListOf<String>()
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var currentPerson = People("", "", "", null)

    var uploadInProgress by mutableStateOf(false)

    var img: Bitmap? = null
    var downloadInProgress by mutableStateOf(false)
    var downloadMessage by mutableStateOf("")


    fun nextPhrase() {
        modelScope.launch {
            phrases.add(ser.getData())
        }
    }

    fun handlePeople() {
        chatList.add(People("Lio", message, mainTopic + "/Lio", loadImage(R.drawable.leo)))
        chatList.add(People("Milena", message, mainTopic + "/Milena", loadImage(R.drawable.milena)))
        chatList.add(People("Martin", message, mainTopic + "/Martin", loadImage(R.drawable.martin)))
        chatList.add(People("Lea", message, mainTopic + "/Lea", loadImage(R.drawable.lea)))
    }

    fun connectAndSubscribe() {
        mqttConnector.connectAndSubscribe(
            topic = mainTopic + "/" + me,
            onNewMessage = {
                val flap = Flap(it)
                checkMsg(flap)
                allFlaps.add(flap)
            },
            onError = { _, p ->
                notificationMessage = p
            }
        )
    }

    fun publish(name: String) {
        currentPerson.text = message
        val flap = Flap(
            sender = me, receiver = name,
            message = message, imageUrl = imageURL, gps = gps
        )
        if (imageURL != "") {
            flap.imageBitmap = img
            currentPerson.text = "\uD83D\uDCF8" + " " + "Photo"
        }

        if (gps.longitude != ""){
            currentPerson.text = "\uD83D\uDCCDLocation"
        }

        mqttConnector.publish(
            topic = mainTopic + "/" + name,
            message = flap,
        )
        allFlaps.add(flap)
        gps = Gps()
        imageURL = ""
    }

    fun uploadToFileIO(bitmap: Bitmap, name: String) {
        uploadInProgress = true
        modelScope.launch {
            uploadBitmapToFileIO(bitmap = bitmap,
                onSuccess = {
                    img = bitmap
                    imageURL = it
                    publish(name)
                },
                onError = { _, _ -> println("Error image url") })
            uploadInProgress = false
        }

    }

    fun downloadFromFileIO(flap: Flap) {
        downloadInProgress = true
        modelScope.launch {
            downloadBitmapFromFileIO(url = flap.imageUrl,
                onSuccess = {
                    flap.imageBitmap = it
                },
                onDeleted = { downloadMessage = "File is deleted" },
                onError = { downloadMessage = "Connection failed" })
            downloadInProgress = false
        }
    }

    private fun checkMsg(flap: Flap) {
        if (flap.imageUrl != "") {
            downloadFromFileIO(flap)
        }
        if (flap.gps.latitude != "" && flap.gps.longitude != "") {
            location = gpsToGeo(flap.gps)
        }
    }

    fun geoToGps(geoPosition: GeoPosition, name: String) {
        gps = Gps(geoPosition.longitude.toString(), geoPosition.latitude.toString())
        publish(name)
    }

    fun gpsToGeo(gps: Gps): GeoPosition {
        return GeoPosition(gps.longitude.toDouble(), gps.latitude.toDouble())
    }

    fun messagesWithCurrentPerson(name: String): List<Flap> =
        allFlaps.filter { it.sender == name || it.receiver == name }

    fun loadImage(@DrawableRes id: Int): ImageBitmap {
        return BitmapFactory.decodeResource(context.resources, id).asImageBitmap()
    }
}