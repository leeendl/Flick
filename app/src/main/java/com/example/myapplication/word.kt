package com.example.myapplication

data class readings(val kanji: String, val kana: String, val furigana: String)

val word = listOf(
    readings("大丈夫", "だいじょうぶ", "だい じょ うぶ"),
    readings("今日", "きょう", "き ょう"),
    readings("食べる", "たべる", "た"),
    readings("見る", "みる", "み"),
    readings("行く", "いく", "い"),
)