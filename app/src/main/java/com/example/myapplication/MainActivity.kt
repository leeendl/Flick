package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
                            text = {
                                Row {
                                    Text("AMOLED", fontSize = 26.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(
                                        checked = amoled,
                                        onCheckedChange = { amoled = it }
                                    )
                                }
                            }
                        )
                    }
                    val kana = listOf("きょう", "ともだち", "ひる") // ...
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var reKana by remember { mutableStateOf(kana.random()) }
                        Text(
                            text = reKana, fontSize = 60.sp,
                            modifier = Modifier
                                .border(3.dp, Color.DarkGray, shape = RoundedCornerShape(8.dp))
                                .padding(9.dp)
                        )
                        var userInput by remember { mutableStateOf("") }
                        TextField(
                            value = userInput,
                            onValueChange = {
                                userInput = it
                                if (it == reKana) {
                                    reKana = kana.random()
                                    userInput = ""
                                }
                            },
                            textStyle = TextStyle(fontSize = 38.sp, textAlign = TextAlign.Center),
                            isError = userInput != reKana && userInput.isNotEmpty(),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(32.dp)
                                .width(reKana.length.dp * 60),
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