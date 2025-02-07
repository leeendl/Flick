package flick

data class Readings(val kanji: String, val kana: String, val english: String)

val word = arrayOf(
    Readings("大丈夫", "だい じょう ぶ", "(is) all right; OK"),
    Readings("今", "いま ", "now"),
    Readings("今日", "き ょう", "today"),
    Readings("食べる", "た ", "(to) eat"),
    Readings("見る", "み ", "(to) see"),
    Readings("行く", "い ", "(to) go"),
    Readings("自分", "じ ぶん", "oneself"),
    Readings("写真", "しゃ しん", "photo"),
    Readings("新しい", "あたら ", "new"),
    Readings("お金", " かね", "money"),
    Readings("来る", "く ", "coming"),

    // I will list non-kanji(s) here for organization
    Readings("いつ", "いつ", "when"),
    Readings("でも", "でも", "but"),
    Readings("じゃあ", "じゃあ", "well then; so")
)