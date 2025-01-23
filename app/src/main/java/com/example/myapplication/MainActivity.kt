package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private var kana = arrayOf("きょう", "ともだち", "ひる") // ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val reKana = kana.random()
                    Box {
                        Text(
                            text = reKana, fontSize = 60.sp,
                            modifier = Modifier
                                .offset(
                                    360.dp,
                                    160.dp
                                )
                                .border(3.dp, Color.DarkGray)
                                .padding(9.dp)
                        )

                    }
                }
            }
        }
    }
}