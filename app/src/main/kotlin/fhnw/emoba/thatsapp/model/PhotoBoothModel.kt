package fhnw.emoba.thatsapp.model

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.thatsapp.data.CameraAppConnector
import fhnw.emoba.thatsapp.data.Flap

class PhotoBoothModel(private val cameraAppConnector: CameraAppConnector) {

    var notificationMessage by mutableStateOf("")

    fun takePhoto(model: ThatsAppModel, name: String) {
        cameraAppConnector.getBitmap(onSuccess  = {
                                                   model.uploadToFileIO(it,name)
                                                  },
                                     onCanceled = { notificationMessage = "Kein neues Bild"})
    }

    fun rotatePhoto(img: Bitmap):Bitmap {
          return  img.rotate(90f)
    }
}

private fun Bitmap.rotate(degrees: Float) : Bitmap {
    val matrix = Matrix().apply {
        postRotate(degrees)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)

}


