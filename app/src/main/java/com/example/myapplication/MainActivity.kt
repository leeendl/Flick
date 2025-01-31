package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var amoled by remember { mutableStateOf(false) }
            MyApplicationTheme(amoled) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var settings by remember { mutableStateOf(false) }
                    var themes by remember { mutableStateOf(false) }
                    TopAppBar(
                        title = { },
                        navigationIcon = {
                            IconButton(
                                onClick = { settings = true },
                                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Settings,
                                    contentDescription = "",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    )
                    if (settings) {
                        AlertDialog(
                            confirmButton = { },
                            onDismissRequest = { settings = false },
                            modifier = Modifier
                                .width(400.dp)
                                .height(200.dp),
                            text = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { themes = true }
                                        .border(2.dp, Color.DarkGray)
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Theme",
                                        fontSize = 26.sp,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    IconButton(
                                        onClick = { themes = true },
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                            contentDescription = "",
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                            }
                        )
                    }
                    if (themes)
                    {
                        AlertDialog(
                            confirmButton = { },
                            onDismissRequest = { themes = false },
                            modifier = Modifier
                                .width(280.dp)
                                .height(230.dp),
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("AMOLED", fontSize = 26.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = amoled,
                                        onClick = { amoled = !amoled }
                                    )
                                }
                            }
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var reWord by remember { mutableStateOf(word.random()) }
                        val kanji = reWord.first
                        val kana = reWord.second

                        Text(
                            text = kana, fontSize = 30.sp,
                            modifier = Modifier
                                .padding(9.dp)
                        )
                        Text(
                            text = kanji, fontSize = 60.sp,
                            modifier = Modifier
                                .border(3.dp, Color.DarkGray, shape = RoundedCornerShape(8.dp))
                                .padding(9.dp)
                        )
                        var userInput by remember { mutableStateOf("") }
                        TextField(
                            value = userInput,
                            onValueChange = {
                                userInput = it
                                if (it == kanji || it == kana) {
                                    reWord = word.random()
                                    userInput = ""
                                }
                            },
                            textStyle = TextStyle(fontSize = 38.sp, textAlign = TextAlign.Center),
                            isError = !(userInput == kanji || userInput == kana) && !kanji.startsWith(userInput) && !kana.startsWith(userInput),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(32.dp)
                                .width(kanji.length.dp * 120),
                            colors = TextFieldDefaults.colors(
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    }
}