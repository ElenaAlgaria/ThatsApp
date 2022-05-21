package fhnw.emoba.modules.module09.gps.data

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices


/*
 Eintrag im AndroidManifest.xml

     <!-- Zugriff auf den GPS Sensor -->
     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>


 Dependency im build.gradle (emoba.app)

    implementation 'com.google.android.gms:play-services-location:19.0.1'

 Emulator mit 'Google Play' verwenden, z.B. 'Pixel 4'

 Im Emulator
  - in emulator-settings die Location setzen
  - Google Maps aufrufen

*/

class GPSConnector(val activity: Activity) {

    private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                      Manifest.permission.ACCESS_COARSE_LOCATION)

    private val locationProvider by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    init {
        ActivityCompat.requestPermissions(activity, PERMISSIONS, 10)
    }

    @SuppressLint("MissingPermission")
    fun getLocation(onNewLocation:      (geoPosition: GeoPosition) -> Unit,
                    onFailure:          (exception: Exception)     -> Unit,
                    onPermissionDenied: ()                         -> Unit)  {
        if (PERMISSIONS.oneOfGranted()) {

            locationProvider.lastLocation   // das ist ein 'Task'
                .addOnSuccessListener(activity) {
                        onNewLocation(GeoPosition(it.longitude, it.latitude))
                }
                .addOnFailureListener(activity) {
                    onFailure(it)
                }
        }
        else {
            onPermissionDenied()
        }
    }

    private fun Array<String>.oneOfGranted(): Boolean = any { it.granted() }

    private fun String.granted(): Boolean = ActivityCompat.checkSelfPermission(activity, this) == PackageManager.PERMISSION_GRANTED
}

