package flick

data class Readings(val kanji: String, val kana: String, val furigana: String)

val word = arrayOf(
    Readings("大丈夫", "だいじょうぶ", "だい じょ うぶ"),
    Readings("今日", "きょう", "き ょう"),
    Readings("食べる", "たべる", "た"),
    Readings("見る", "みる", "み"),
    Readings("行く", "いく", "い"),
    Readings("自分", "じぶん", "じ ぶん"),
    Readings("写真", "しゃしん", "しゃ しん"),
    Readings("新しい", "じぶん", "あたら"),
    Readings("お金", "おかね", " かね"),
    Readings("来る", "くる", "く"),

    // I will list non-kanji(s) here for organization
    Readings("いつ", "いつ", ""),
    Readings("でも", "でも", ""),
    Readings("じゃあ", "じゃあ", "")
)