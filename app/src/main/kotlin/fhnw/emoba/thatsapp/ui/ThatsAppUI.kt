package fhnw.emoba.thatsapp.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.*
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.PhotoBoothModel
import fhnw.emoba.thatsapp.model.ThatsAppModel
import fhnw.emoba.thatsapp.ui.screens.OverviewChat
import fhnw.emoba.thatsapp.ui.screens.Profil


@Composable
fun AppUI(model: ThatsAppModel, modelPhotoBoothModel: PhotoBoothModel) {
    val scaffoldState = rememberScaffoldState()
    MaterialTheme(colors = lightColors(primary = lightBlue100)) {
        Scaffold(scaffoldState = scaffoldState,
            content = { Body(model, modelPhotoBoothModel) }
        )
    }
}
/*
@Composable
private fun Bar(model: ThatsAppModel) {
    with(model) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Person, contentDescription = "Profil")
                }
            },
            contentColor = Color.Black,
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
        )
    }
}

 */


@Composable
private fun Body(model: ThatsAppModel, modelPhotoBoothModel: PhotoBoothModel) {
       Column(modifier = Modifier.fillMaxWidth()) {
           Crossfade(targetState = model.currentScreen) { screen ->
               when (screen){
                    AvailableScreen.CHAT -> {
                        Chat(model = model, modelPhotoBoothModel = modelPhotoBoothModel)
                    }
                   AvailableScreen.OVERVIEW -> {
                        OverviewChat(model = model)
                   }
                   AvailableScreen.PROFIL -> {
                        Profil(model = model)
                   }
               }

           }
       }
}
