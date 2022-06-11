package fhnw.emoba.thatsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.R
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.lightBlue100
import fhnw.emoba.thatsapp.data.People
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.ThatsAppModel


@Composable
fun NewPerson(model: ThatsAppModel) {
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
            title = { Text("Add a new Person") },
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
private fun Body(model: ThatsAppModel) {
    with(model) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

            Name(textLbl = "Name", model = model)

            Spacer(modifier = Modifier.padding(0.dp, 20.dp))

            Button(onClick = {
                        model.chatList.add(
                            People(
                                model.newPerson, message, mainTopic + "/${model.newPerson}", loadImage(
                                    R.drawable.profil
                                )
                            )
                        )

                currentScreen = AvailableScreen.OVERVIEW
            }) {
                Text("Save", color = Color.Black)
            }

        }
    }
}

@Composable
private fun Name(textLbl: String, model: ThatsAppModel) {
    with(model){
        TextField(
            value = newPerson,
            onValueChange = {newPerson = it},
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White, focusedLabelColor = Color.DarkGray),
            label = { Text(text = textLbl)},

        )
    }
}
