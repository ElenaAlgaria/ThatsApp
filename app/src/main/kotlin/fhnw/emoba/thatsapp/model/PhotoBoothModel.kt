package fhnw.emoba.thatsapp.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.thatsapp.data.CameraAppConnector

class PhotoBoothModel(private val cameraAppConnector: CameraAppConnector) {

    var photo by mutableStateOf<Bitmap?>(null)

    var notificationMessage by mutableStateOf("")

    fun takePhoto(model: ThatsAppModel) {
        cameraAppConnector.getBitmap(onSuccess  = { photo = it
                                                   model.uploadToFileIO(photo!!)},
                                     onCanceled = { notificationMessage = "Kein neues Bild"})
    }

    fun rotatePhoto() {
        photo?.let {
                photo = photo!!.rotate(90f)
            }
    }
}

private fun Bitmap.rotate(degrees: Float) : Bitmap {
    val matrix = Matrix().apply {
        postRotate(degrees)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}


