package fhnw.emoba.thatsapp.model

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
import fhnw.emoba.modules.module07.flutter_solution.model.Flap
import org.json.JSONObject

class ThatsAppModel(private val context: ComponentActivity) {
    val title      = "ThatsApp"
    val mqttBroker = "broker.hivemq.com"
    val mainTopic  = "fhnw/emoba/thatsapp"
    val allFlaps = mutableStateListOf<Flap>()


    var notificationMessage by mutableStateOf("")
    var flapsPublished      by mutableStateOf(0)
    var message             by mutableStateOf("Hi")
    var me         by  mutableStateOf("Elena")

    var currentScreen by mutableStateOf(AvailableScreen.CHAT)
    val imagePic   = loadImage(R.drawable.character)

    private val mqttConnector by lazy { MqttConnector(mqttBroker) }
    private val soundPlayer   by lazy { MediaPlayer.create(context, R.raw.exp) }

    fun connectAndSubscribe(){
        mqttConnector.connectAndSubscribe(
            topic        = mainTopic,
            onNewMessage = { allFlaps.add(Flap(it))
                playSound()
            },
            onError      = {_, p ->
                notificationMessage = p
                playSound()
            }
        )
    }

    fun publish(){
        mqttConnector.publish(
            topic       = mainTopic,
            message     = Flap(sender  = me,
                message = message))
        allFlaps.add(Flap(sender = me, message = message))
    }

    private fun playSound(){
        soundPlayer.seekTo(0)
        soundPlayer.start()
    }

    private fun loadImage(@DrawableRes id: Int) : ImageBitmap {
        return BitmapFactory.decodeResource(context.resources, id).asImageBitmap()
    }
}