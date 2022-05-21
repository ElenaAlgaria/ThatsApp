package fhnw.emoba.thatsapp.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.thatsapp.data.Flap
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.*
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.GpsModel
import fhnw.emoba.thatsapp.model.PhotoBoothModel
import fhnw.emoba.thatsapp.model.ThatsAppModel


@Composable
fun Chat(model: ThatsAppModel, modelPhotoBoothModel: PhotoBoothModel, gpsModel: GpsModel) {
    val scaffoldState = rememberScaffoldState()
    MaterialTheme(colors = lightColors(primary = lightBlue100)) {
        Scaffold(scaffoldState = scaffoldState,
            topBar = { Bar(model) },
            snackbarHost = { NotificationHost(it) },
            content = { Body(model, modelPhotoBoothModel, gpsModel) }
        )
    }
    Notification(model, scaffoldState)
}

@Composable
private fun Bar(model: ThatsAppModel) {
    with(model) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = { currentScreen = AvailableScreen.OVERVIEW }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            contentColor = Color.Black,
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
        )
    }
}

@Composable
private fun NotificationHost(state: SnackbarHostState) {
    SnackbarHost(state) { data ->
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Snackbar(snackbarData = data)
        }
    }
}

@Composable
private fun Body(model: ThatsAppModel, modelPhotoBoothModel: PhotoBoothModel, gpsModel: GpsModel) {
    with(model) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (selfie, topicInfo, allFlapsPanel, message) = createRefs()

            if (modelPhotoBoothModel.photo != null) {
                Photo(bitmap = modelPhotoBoothModel.photo!!,
                    model = modelPhotoBoothModel,
                    modifier = Modifier.constrainAs(selfie) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(parent.start, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        width = Dimension.fillToConstraints
                    })
            }

            Info("", Modifier.constrainAs(topicInfo) {})

            AllFlapsPanel(
                messagesWithCurrentPerson(currentPerson.name),
                Modifier.constrainAs(allFlapsPanel) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(topicInfo.bottom, 15.dp)
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(message.top, 15.dp)
                })

            NewMessage(model, modelPhotoBoothModel, gpsModel, Modifier.constrainAs(message) {
                //  width = Dimension.fillToConstraints
                start.linkTo(parent.start, 10.dp)
                end.linkTo(parent.end, 5.dp)
                bottom.linkTo(parent.bottom, 20.dp)
            })
        }
    }
}

@Composable
private fun AllFlapsPanel(flaps: List<Flap>, modifier: Modifier) {
    Box(
        modifier.border(
            width = 1.dp,
            brush = SolidColor(gray300),
            shape = RectangleShape
        )
    ) {

        AllFlaps(flaps)
    }
}


@Composable
private fun AllFlaps(flaps: List<Flap>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(flaps) { SingleFlap(it) }
    }

    LaunchedEffect(flaps.size) {
        scrollState.animateScrollToItem(flaps.size)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SingleFlap(flap: Flap) {
    with(flap) {
        if (message != "") {
            ListItem(text = { Text(message) },
                overlineText = { Text(sender) }
            )
        } else {
            ListItem(text = { Text(gps.longitude + " " + gps.latitude) },
                overlineText = { Text(sender) }
            )
        }
        Divider()
    }
}

@Composable
private fun Info(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        modifier = modifier
    )
}

@Composable
private fun Photo(bitmap: Bitmap, model: PhotoBoothModel, modifier: Modifier) {
    with(model) {
        Image(bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            modifier = modifier.clickable(onClick = { rotatePhoto() })
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun NewMessage(
    model: ThatsAppModel,
    modelPhotoBoothModel: PhotoBoothModel,
    gpsModel: GpsModel,
    modifier: Modifier
) {
    with(model) {
        Row(modifier = modifier) {
            val keyboard = LocalSoftwareKeyboardController.current
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboard?.hide() }),
                shape = RoundedCornerShape(20.dp), textStyle = TextStyle(fontSize = 14.sp)

            )
            IconButton(onClick = { modelPhotoBoothModel.takePhoto() }) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "pic")

            }
            IconButton(onClick = {
                message = gpsModel.rememberCurrentPosition(model)
                loc = true
            }) {
                Icon(Icons.Filled.LocationSearching, contentDescription = "loc")
            }

            IconButton(onClick = {
                publish(currentPerson.name)
                message = ""
            }) {
                Icon(Icons.Filled.Send, contentDescription = "send")
            }
        }
    }
}

@Composable
private fun Notification(model: ThatsAppModel, scaffoldState: ScaffoldState) {
    with(model) {
        if (notificationMessage.isNotBlank()) {
            LaunchedEffect(scaffoldState.snackbarHostState) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = notificationMessage,
                    actionLabel = "OK"
                )
                notificationMessage = ""
            }
        }
    }
}
