package fhnw.emoba.thatsapp.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.lightBlue100
import fhnw.emoba.thatsapp.data.People
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.ThatsAppModel


@Composable
fun OverviewChat(model: ThatsAppModel) {
    val scaffoldState = rememberScaffoldState()
    MaterialTheme(colors = lightColors(primary = lightBlue100)) {
        Scaffold(scaffoldState = scaffoldState,
            topBar = { Bar(model) },
            content = { Body(model) }
        )
    }

}
@Composable
private fun Bar(model: ThatsAppModel) {
    with(model) {
        TopAppBar(
            title = { Text("Chats") },
            contentColor = Color.Black,
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)),
            actions = {
                IconButton(onClick = { currentScreen = AvailableScreen.PROFIL }) {
                    Icon(Icons.Filled.Person, contentDescription = "Profil")
                }
            }
        )
    }
}

@Composable
private fun Body(model: ThatsAppModel) {
    val state = rememberLazyListState()
    with(model) {
        LazyColumn(state = state, modifier = Modifier.fillMaxWidth()){
            items(chatList){
                Overview(it, model)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Overview(it: People, model: ThatsAppModel) {
    with(model) {
        Card(shape = RoundedCornerShape(20.dp), elevation = 10.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            onClick = {
             //   setTrack(trackList.indexOf(it), it, it.album,it.artist)
                currentScreen = AvailableScreen.CHAT
            }) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ListItem(text = {  Text(
                    text = it.name, fontSize = 18.sp
                )}, secondaryText = {Text(
                    text = it.text, modifier = Modifier
                        .padding(10.dp)
                        .align(
                            Alignment.Start
                        ), fontSize = 14.sp, color = Color.DarkGray
                )})
                    ImageProfil(it.loadImage)


            }
        }
    }
}

@Composable
private fun ImageProfil(image: ImageBitmap) {
    Image(
        bitmap = image,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.graphicsLayer(
            shape = CircleShape)
        )
}
