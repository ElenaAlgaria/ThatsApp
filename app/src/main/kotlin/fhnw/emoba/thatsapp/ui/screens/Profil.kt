package fhnw.emoba.thatsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.lightBlue100
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
                IconButton(onClick = { currentScreen = AvailableScreen.CHAT }) {
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

        //   Panini(bitmap = imagePic, modifier = Modifier.fillMaxSize())

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (pic, name) = createRefs()

            val margin = 20.dp

            Image(imagePic, modifier = Modifier.constrainAs(pic) {
                top.linkTo(parent.top, margin)
                start.linkTo(parent.start, margin)
                end.linkTo(parent.end, margin)
                width = Dimension.fillToConstraints
            })

            ProfilTxt("Name", model, Modifier.constrainAs(name) {
                start.linkTo(parent.start,margin)
                top.linkTo(pic.bottom, margin)
                end.linkTo(parent.end, margin)
                width = Dimension.fillToConstraints
            })

        }
    }
}

@Composable
private fun ProfilTxt(textLbl: String, model: ThatsAppModel, modifier: Modifier) {
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

}

@Composable
private fun Image(bitmap: ImageBitmap, modifier: Modifier) {
    Image(
        bitmap = bitmap,
        contentDescription = "",
        contentScale = ContentScale.FillHeight,
        modifier = modifier.graphicsLayer(
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            clip = true
        )
    )
}