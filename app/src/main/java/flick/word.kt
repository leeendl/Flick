package flick

data class Readings(val kanji: String, val kana: String, val furigana: String, val english: String)

val word = arrayOf(
    Readings("大丈夫", "だいじょうぶ", "だい じょう ぶ", "(is) all right; OK"),
    Readings("今", "いま", "いま", "now"),
    Readings("今日", "きょう", "き ょう", "today"),
    Readings("食べる", "たべる", "た", "(to) eat"),
    Readings("見る", "みる", "み", "(to) see"),
    Readings("行く", "いく", "い", "(to) go"),
    Readings("自分", "じぶん", "じ ぶん", "oneself"),
    Readings("写真", "しゃしん", "しゃ しん", "photo"),
    Readings("新しい", "じぶん", "あたら", "new"),
    Readings("お金", "おかね", " かね", "money"),
    Readings("来る", "くる", "く", "coming"),

    // I will list non-kanji(s) here for organization
    Readings("いつ", "いつ", "", "when"),
    Readings("でも", "でも", "", "but"),
    Readings("じゃあ", "じゃあ", "", "well then; so")
)