package fhnw.emoba.thatsapp.model

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AndroidUriHandler
import fhnw.emoba.modules.module09.gps.data.GPSConnector
import fhnw.emoba.modules.module09.gps.data.GeoPosition
import fhnw.emoba.thatsapp.data.Gps

class GpsModel(private val activity: ComponentActivity,
               private val locator:  GPSConnector) {
    var waypoints = GeoPosition()
    var notificationMessage by mutableStateOf("")

    fun rememberCurrentPosition(thatsAppModel: ThatsAppModel) : String{
            locator.getLocation(onNewLocation      = { waypoints = it
                                                     thatsAppModel.gps = thatsAppModel.geoToGps(it)
                                                     },
                                onFailure          = {},
                                onPermissionDenied = { notificationMessage = "Keine Berechtigung." },
            )
        return thatsAppModel.gps.longitude + " " + thatsAppModel.gps.latitude
    }

    fun showOnMap(position: GeoPosition) = AndroidUriHandler(activity).openUri(position.asOpenStreetMapsURL())
}

