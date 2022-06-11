package fhnw.emoba.thatsapp.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.*
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.GpsModel
import fhnw.emoba.thatsapp.model.PhotoBoothModel
import fhnw.emoba.thatsapp.model.ThatsAppModel
import fhnw.emoba.thatsapp.ui.screens.*


@Composable
fun AppUI(model: ThatsAppModel, modelPhotoBoothModel: PhotoBoothModel, gpsModel: GpsModel) {
    val scaffoldState = rememberScaffoldState()
    MaterialTheme(colors = lightColors(primary = lightBlue100)) {
        Scaffold(scaffoldState = scaffoldState,
            content = { Body(model, modelPhotoBoothModel, gpsModel) }
        )
    }
}

@Composable
private fun Body(model: ThatsAppModel, modelPhotoBoothModel: PhotoBoothModel, gpsModel: GpsModel) {
       Column(modifier = Modifier.fillMaxWidth()) {
           Crossfade(targetState = model.currentScreen) { screen ->
               when (screen){
                    AvailableScreen.CHAT -> {
                        Chat(model = model, modelPhotoBoothModel = modelPhotoBoothModel, gpsModel = gpsModel)
                    }
                   AvailableScreen.OVERVIEW -> {
                        OverviewChat(model = model)
                   }
                   AvailableScreen.PROFIL -> {
                        Profil(model = model)
                   }
                   AvailableScreen.ROBOTO -> {
                       Roboto(model = model)
                   }
                   AvailableScreen.ADD_PERSON -> {
                       NewPerson(model = model)
                   }
               }

           }
       }
}
