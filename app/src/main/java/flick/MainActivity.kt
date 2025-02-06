package flick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import flick.ui.theme.Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var dark by remember { mutableStateOf(true) }
            Theme(dark) {
                Surface {
                    var settings by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { settings = true },
                        modifier = Modifier.padding(start = 16.dp, top = 30.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = ""
                        )
                    }
                    var themes by remember { mutableStateOf(false) }
                    var showFurigana by remember { mutableStateOf(false) }
                    if (settings) {
                        Dialog(
                            onDismissRequest = { settings = false }
                        ) {
                            Surface(
                                modifier = Modifier
                                    .width(380.dp),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .clickable { themes = true }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Theme",
                                            fontSize = 26.sp,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = if (dark) "Dark" else "Light",
                                            color = Color.Gray,
                                            fontSize = 18.sp,
                                        )
                                        IconButton(
                                            onClick = { themes = true },
                                        ) {
                                            Icon(
                                                painterResource(R.drawable.ic_keyboard_arrow_right),
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .clickable { showFurigana = !showFurigana }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Furigana",
                                            fontSize = 26.sp,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Switch(
                                            checked = showFurigana,
                                            onCheckedChange = { showFurigana = it }
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
                                RadioButton(
                                    selected = dark,
                                    onClick = { dark = true }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .clickable { dark = !dark }
                                    .padding(16.dp)
                            ) {
                                Text("Light", fontSize = 26.sp)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(
                                    selected = !dark,
                                    onClick = { dark = false }
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var reWord by remember { mutableStateOf(word.random()) }
                        var wordLookup by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .border(2.dp, Color.DarkGray, MaterialTheme.shapes.medium)
                                .padding(9.dp)
                                .clickable {
                                    wordLookup = true
                                }
                        ) {
                            if (reWord.furigana.isEmpty()) {
                                Text(
                                    text = reWord.kana,
                                    fontSize = 60.sp
                                )
                            } else {
                                val entries = reWord.kanji.toCharArray().mapIndexed { i, kanji ->
                                    Pair(kanji.toString(),
                                        reWord.furigana.split(" ").getOrElse(i) { "" })
                                }
                                Row(
                                    modifier = Modifier.padding(bottom = 2.dp)
                                ) {
                                    entries.forEach { (kanji, furigana) ->
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            if (showFurigana) {
                                                Text(
                                                    text = furigana,
                                                    fontSize = 20.sp
                                                )
                                            }
                                            Text(
                                                text = kanji,
                                                fontSize = 60.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (wordLookup)
                        {
                            Dialog(
                                onDismissRequest = { wordLookup = false }
                            ) {
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    modifier = Modifier
                                        .width(240.dp)
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
                                if (it == reWord.kanji || it == reWord.kana) {
                                    reWord = word.random()
                                    userInput = ""
                                }
                            },
                            textStyle = TextStyle(fontSize = 40.sp),
                            isError = !(userInput == reWord.kanji || userInput == reWord.kana) && !reWord.kanji.startsWith(userInput) && !reWord.kana.startsWith(userInput),
                            modifier = Modifier
                                .padding(32.dp)
                                .width(reWord.kanji.length.dp * 72),
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