package fhnw.emoba.thatsapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.thatsapp.data.CameraAppConnector
import fhnw.emoba.thatsapp.model.PhotoBoothModel
import fhnw.emoba.thatsapp.model.ThatsAppModel
import fhnw.emoba.thatsapp.ui.AppUI


object ThatsApp : EmobaApp {
    private lateinit var model: ThatsAppModel
    private lateinit var modelPhotoBoothModel: PhotoBoothModel

    override fun initialize(activity: ComponentActivity) {
        model = ThatsAppModel(activity)
        val cameraAppConnector = CameraAppConnector(activity)
        modelPhotoBoothModel = PhotoBoothModel(cameraAppConnector)
        model.connectAndSubscribe()
        model.handlePeople()
    }

    @Composable
    override fun CreateUI() {
        AppUI(model, modelPhotoBoothModel)
    }

}

