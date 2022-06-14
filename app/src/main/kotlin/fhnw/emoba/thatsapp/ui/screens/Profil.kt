package fhnw.emoba.thatsapp.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.R
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.lightBlue100
import fhnw.emoba.thatsapp.data.People
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.ThatsAppModel

@Composable
fun Profil(model: ThatsAppModel) {
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
            title = { Text("Profil") },
            navigationIcon = {
                IconButton(onClick = {
                    currentScreen = AvailableScreen.OVERVIEW }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            contentColor = Color.Black,
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
        )
    }
}

@Composable
private fun Body(model: ThatsAppModel) {
    with(model) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (pic, name, greeting, btnSave) = createRefs()

            val margin = 20.dp

            ImageProfil(imagePic, modifier = Modifier.constrainAs(pic) {
                top.linkTo(parent.top, margin)
                start.linkTo(parent.start, margin)
                end.linkTo(parent.end, margin)
                bottom.linkTo(name.top, margin)

            })

            ProfilName("Name", model, Modifier.constrainAs(name) {
                start.linkTo(parent.start,35.dp)
                top.linkTo(pic.bottom, margin)
                end.linkTo(parent.end, 35.dp)
                bottom.linkTo(greeting.top, margin)
                width = Dimension.fillToConstraints
            })

            ProfilTxt("Status", model, Modifier.constrainAs(greeting) {
                start.linkTo(parent.start,35.dp)
                top.linkTo(name.bottom, margin)
                end.linkTo(parent.end, 35.dp)
                bottom.linkTo(btnSave.top, margin)
                width = Dimension.fillToConstraints
            })

            Button(onClick = {model.mqttConnector.disconnect()
                Thread.sleep(500)
                model.connectAndSubscribe()
                currentScreen = AvailableScreen.OVERVIEW
                 }, Modifier.constrainAs(btnSave){
                top.linkTo(greeting.bottom, margin)
                start.linkTo(parent.start, margin)
                end.linkTo(parent.end, margin)
                bottom.linkTo(parent.bottom, margin)

            }) {
                Text("Save", color = Color.Black)
            }

        }
    }
}

@Composable
private fun ProfilName(textLbl: String, model: ThatsAppModel, modifier: Modifier) {
    with(model){
        TextField(
            value = me,
            onValueChange = {me = it},
            textStyle = MaterialTheme.typography.subtitle1,
           singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White, focusedLabelColor = Color.DarkGray),
            label = { Text(text = textLbl)},
            modifier = modifier

        )
    }

}@Composable
private fun ProfilTxt(textLbl: String, model: ThatsAppModel, modifier: Modifier) {
    with(model){
        TextField(
            value = greeting,
            onValueChange = {greeting = it},
            textStyle = MaterialTheme.typography.subtitle1,
           singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White, focusedLabelColor = Color.DarkGray),
            label = { Text(text = textLbl)},
            modifier = modifier

        )
    }

}

@Composable
private fun ImageProfil(defaultBitmap: ImageBitmap, modifier: Modifier) {

    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }


    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver,it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver,it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }

    }

    if (bitmap.value == null){
        Image(
            bitmap = defaultBitmap,
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .graphicsLayer(
                    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    clip = true
                )
                .clickable(onClick = { launcher.launch("image/*") })
        )
    } else {
        Image(
            bitmap = bitmap.value!!.asImageBitmap(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .graphicsLayer(
                    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    clip = true
                )
                .clip(CircleShape)
                .size(210.dp)
                .clickable(onClick = { launcher.launch("image/*") }),

        )
    }

}



