package flick

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import flick.ui.theme.Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPreferences = remember { getSharedPreferences("settings", Context.MODE_PRIVATE) }
            Display(
                sharedPreferences
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Display(
    sharedPreferences: SharedPreferences
) {
    var dark by remember { mutableStateOf(true) }
    var settings by remember { mutableStateOf(false) }
    var themes by remember { mutableStateOf(false) }
    var showFurigana by remember {
        mutableStateOf(sharedPreferences.getBoolean("furigana", false))
    }
    Theme(dark) {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = { settings = true },
                    modifier = Modifier.padding(start = 16.dp, top = 30.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_settings),
                        contentDescription = "Settings"
                    )
                }
                var reWord by remember { mutableStateOf(word.random()) }
                var wordLookup by remember { mutableStateOf(false) }
                val spaces = reWord.furigana.split(" ")
                val kana = if (reWord.furigana.contains(" ")) getKana(
                    reWord.kanji,
                    spaces
                ) else reWord.furigana

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .border(2.dp, Color.DarkGray, MaterialTheme.shapes.medium)
                            .padding(16.dp)
                            .clickable { wordLookup = true }
                    ) {
                        if (!reWord.kanji.any { it.isKanji() }) {
                            Text(text = kana, fontSize = 60.sp)
                        } else {
                            Row {
                                reWord.kanji.mapIndexed { i, kanji ->
                                    kanji.toString() to spaces.getOrElse(i) { "" }
                                }.forEach { (kanji, furigana) ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        if (showFurigana) {
                                            Text(text = furigana, fontSize = 22.sp)
                                        }
                                        Box {
                                            Text(text = kanji, fontSize = 60.sp)
                                            if (!kanji.any { it.isKana() }) {
                                                Canvas(
                                                    modifier = Modifier
                                                        .matchParentSize()
                                                        .padding(top = 18.dp + 60.dp + 4.dp),
                                                    onDraw = {
                                                        drawLine(
                                                            color = when (reWord.jpl) {
                                                                5u.toUByte() -> Color(0xFF82DE25)
                                                                3u.toUByte() -> Color(0xFFE0C425)
                                                                2u.toUByte() -> Color(0xFFE08625)
                                                                else -> Color.Transparent
                                                            },
                                                            start = Offset.Zero,
                                                            end = Offset(size.width, 0f),
                                                            strokeWidth = 4f
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (wordLookup) {
                        Dialog(onDismissRequest = { wordLookup = false }) {
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                modifier = Modifier.width(240.dp)
                            ) {
                                Text(
                                    text = reWord.english,
                                    fontSize = 40.sp,
                                    lineHeight = 46.sp,
                                    modifier = Modifier.padding(20.dp)
                                )
                            }
                        }
                    }
                    var userInput by remember { mutableStateOf("") }
                    TextField(
                        value = userInput,
                        onValueChange = {
                            userInput = it
                            if (it == reWord.kanji || it == kana) {
                                reWord = word.random()
                                userInput = ""
                            }
                        },
                        textStyle = TextStyle(fontSize = 40.sp),
                        isError = !(userInput == reWord.kanji || userInput == kana) && !reWord.kanji.startsWith(
                            userInput
                        ) && !kana.startsWith(userInput),
                        modifier = Modifier
                            .padding(32.dp)
                            .width(kana.length.dp * 72),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
            if (settings) {
                Dialog(onDismissRequest = { settings = false }) {
                    Surface(
                        modifier = Modifier.width(380.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .clickable { themes = true }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Theme", fontSize = 26.sp)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = if (dark) "Dark" else "Light",
                                    color = Color.Gray,
                                    fontSize = 18.sp
                                )
                                IconButton(onClick = { themes = true }) {
                                    Icon(
                                        painterResource(R.drawable.ic_keyboard_arrow_right),
                                        contentDescription = "Change Theme"
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        showFurigana = !showFurigana
                                        sharedPreferences.edit()
                                            .putBoolean("furigana", !showFurigana).apply()
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Furigana", fontSize = 26.sp)
                                Spacer(modifier = Modifier.weight(1f))
                                Switch(
                                    checked = showFurigana,
                                    onCheckedChange = {
                                        showFurigana = it
                                        sharedPreferences.edit().putBoolean("furigana", it).apply()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (themes) {
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
                    Row(
                        modifier = Modifier
                            .clickable { dark = !dark }
                            .padding(16.dp)
                    ) {
                        Text("Dark", fontSize = 26.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        RadioButton(selected = dark, onClick = { dark = true })
                    }
                    Row(
                        modifier = Modifier
                            .clickable { dark = !dark }
                            .padding(16.dp)
                    ) {
                        Text("Light", fontSize = 26.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        RadioButton(selected = !dark, onClick = { dark = false })
                    }
                }
            }
        }
    }
}