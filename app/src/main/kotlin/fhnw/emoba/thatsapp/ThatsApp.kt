package fhnw.emoba.thatsapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module09.gps.data.GPSConnector
import fhnw.emoba.thatsapp.data.CameraAppConnector
import fhnw.emoba.thatsapp.data.Fact_2.APIFact
import fhnw.emoba.thatsapp.model.GpsModel
import fhnw.emoba.thatsapp.model.PhotoBoothModel
import fhnw.emoba.thatsapp.model.ThatsAppModel
import fhnw.emoba.thatsapp.ui.AppUI


object ThatsApp : EmobaApp {
    private lateinit var model: ThatsAppModel
    private lateinit var modelPhotoBoothModel: PhotoBoothModel
    private lateinit var gpsModel: GpsModel

    override fun initialize(activity: ComponentActivity) {
        val ser = APIFact()
        model = ThatsAppModel(activity, ser)
        val cameraAppConnector = CameraAppConnector(activity)
        modelPhotoBoothModel = PhotoBoothModel(cameraAppConnector)

        model.handlePeople()
        val gps = GPSConnector(activity)
        gpsModel = GpsModel(activity, gps)
    }

    @Composable
    override fun CreateUI() {
        AppUI(model, modelPhotoBoothModel, gpsModel)
    }

}

