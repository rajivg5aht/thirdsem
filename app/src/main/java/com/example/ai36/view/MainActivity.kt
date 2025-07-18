package com.example.ai36.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    MainHeading()

                }
            }
        }
    }
}

@Composable
fun MainHeading() {
    Scaffold { innerPadding ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxHeight().weight(2f).background(color = Color.Red))
            Box(modifier = Modifier.fillMaxHeight().weight(2f).background(color = Color.Gray))
            Box(modifier = Modifier.fillMaxHeight().weight(2f).background(color = Color.Magenta))
//            Text(
//                text = "Hello",
//                textDecoration = TextDecoration.Underline,
//                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
//                fontFamily = FontFamily.SansSerif
//            )
//            Text(text = " world")

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewHeading() {
    MainHeading()
}
