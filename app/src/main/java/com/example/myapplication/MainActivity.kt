package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var amoled by remember { mutableStateOf(false) }
            MyApplicationTheme(amoled) {
                Surface {
                    var settings by remember { mutableStateOf(false) }
                    var themes by remember { mutableStateOf(false) }
                    var furigana by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { settings = true },
                        modifier = Modifier.padding(start = 16.dp, top = 30.dp)
                    ) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    if (settings) {
                        AlertDialog(
                            confirmButton = { },
                            onDismissRequest = { settings = false },
                            modifier = Modifier
                                .width(400.dp)
                                .height(260.dp),
                            text = {
                                Column{
                                    Row(
                                        modifier = Modifier
                                            .clickable { themes = true }
                                            .border(1.dp, Color.DarkGray)
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
                                    Row(
                                        modifier = Modifier
                                            .clickable { furigana = !furigana }
                                            .border(1.dp, Color.DarkGray)
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Furigana",
                                            fontSize = 26.sp,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Switch(
                                            checked = furigana,
                                            onCheckedChange = { furigana = it }
                                        )
                                    }
                                }
                            }
                        )
                    }
                    if (themes)
                    {
                        val scope = rememberCoroutineScope()
                        val sheetState = rememberModalBottomSheetState()
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) themes = false
                                }
                            },
                            sheetState = sheetState
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                Row {
                                    Text("AMOLED", fontSize = 26.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = amoled,
                                        onClick = { amoled = !amoled }
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var reWord by remember { mutableStateOf(word.random()) }
                        if (furigana && reWord.furigana != "") {
                            Box(
                                modifier = Modifier
                                    .border(2.dp, Color.DarkGray, shape = RoundedCornerShape(8.dp))
                                    .padding(9.dp)
                            ) {
                                val entries = reWord.kanji.toCharArray().mapIndexed { i, kanji ->
                                    Pair(kanji.toString(),
                                        reWord.furigana.split(" ").getOrElse(i) { "" })
                                }
                                Row {
                                    entries.forEach { (kanji, furigana) ->
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = furigana,
                                                fontSize = 20.sp,
                                                modifier = Modifier.padding(bottom = 2.dp)
                                            )
                                            Text(
                                                text = kanji,
                                                fontSize = 60.sp,
                                                modifier = Modifier.padding(bottom = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Text(
                                text = reWord.kanji, fontSize = 60.sp,
                                modifier = Modifier
                                    .border(2.dp, Color.DarkGray, shape = RoundedCornerShape(8.dp))
                                    .padding(9.dp)
                            )
                        }
                        var userInput by remember { mutableStateOf("") }
                        TextField(
                            value = userInput,
                            onValueChange = {
                                userInput = it
                                if (it == reWord.kanji || it == reWord.kana) {
                                    reWord = word.random()
                                    userInput = ""
                                }
                            },
                            textStyle = TextStyle(fontSize = 38.sp, textAlign = TextAlign.Center),
                            isError = !(userInput == reWord.kanji || userInput == reWord.kana) && !reWord.kanji.startsWith(userInput) && !reWord.kana.startsWith(userInput),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(32.dp)
                                .width(reWord.kanji.length.dp * 80),
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