package fhnw.emoba.thatsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.R
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.gray300
import fhnw.emoba.modules.module07.flutter_solution.ui.theme.lightBlue100
import fhnw.emoba.thatsapp.model.AvailableScreen
import fhnw.emoba.thatsapp.model.ThatsAppModel

    @Composable
    fun Roboto(model: ThatsAppModel) {
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
            title = { Text("Roboto") },
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
        var visible by remember {
            mutableStateOf(true)
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (pic, greeting, btn, btnMore, phrasesBox) = createRefs()

            val margin = 30.dp

            ImageProfil( model ,modifier = Modifier.constrainAs(pic) {
                top.linkTo(parent.top, margin)
                start.linkTo(parent.start, margin)
                end.linkTo(parent.end, margin)
                width = Dimension.fillToConstraints
            })
            if (visible){
            Msg( Modifier.constrainAs(greeting){
                top.linkTo(pic.bottom, 10.dp)
                start.linkTo(parent.start, margin)
                end.linkTo(parent.end, margin)
                width = Dimension.fillToConstraints
            })

                Button(onClick = { visible = ! visible }, Modifier.constrainAs(btn) {
                    top.linkTo(greeting.bottom, 10.dp)
                    start.linkTo(parent.start, margin)
                    end.linkTo(parent.end, margin)

                }) {
                    Text("Lets go", color = Color.Black)
                }
            } else {

                PhrasesBox(model.phrases, Modifier.constrainAs(phrasesBox){
                    start.linkTo(parent.start, margin)
                    top.linkTo(pic.bottom, 10.dp)
                    end.linkTo(parent.end, margin)
                    bottom.linkTo(btnMore.top, 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })

            Button(onClick = { nextPhrase() }, Modifier.constrainAs(btnMore){
                start.linkTo(parent.start, margin)
                end.linkTo(parent.end, margin)
                bottom.linkTo(parent.bottom, 10.dp)

            }) {
                Text("More", color = Color.Black)
            }

            }


        }
    }
}

@Composable
private fun PhrasesBox(phrases: List<String>, modifier: Modifier){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val scrollState = rememberLazyListState()
        LazyColumn(
            state = scrollState,
           // modifier = Modifier.align(Alignment.TopCenter)

            ) {
            items(phrases) { SinglePhrase(it) }
        }
        LaunchedEffect(phrases.size) {
            scrollState.animateScrollToItem(phrases.size)
        }
    }
    }

@Composable
private fun SinglePhrase(phrase: String){
    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 25.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp), shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ) {

            Text(text     = phrase,
                style    = MaterialTheme.typography.body1,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
    
    
    /*
    Box(modifier = Modifier
        .height(phrase.length.dp + 30.dp)
        .padding(vertical = 30.dp, horizontal = 40.dp)){
        Text(phrase, Modifier.align(Alignment.TopCenter))
    }
   Divider()

     */

}


@Composable
private fun Msg( modifier: Modifier){
    Card(
        modifier = modifier, shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {

    Text(text     = "Hello, its me Roboto! How are you? I hope you are fine :) I'm here to entertain you with some interesting facts ;)",
        style    = MaterialTheme.typography.body1,
        modifier = modifier.padding(10.dp),
        color = MaterialTheme.colors.onSecondary
    )
    }

}

@Composable
private fun ImageProfil(model: ThatsAppModel, modifier: Modifier) {
    with(model){
        Image(
            bitmap =  loadImage(R.drawable.robot),
            contentDescription = "",
            modifier = modifier
                .size(180.dp)
                .padding(10.dp)

        )
    }
}