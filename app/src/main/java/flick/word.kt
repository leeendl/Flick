package flick

data class Readings(val kanji: String, val kana: String, val english: String, val jpl: Short = 0)

val word = arrayOf(
    Readings("大丈夫", "だい じょう ぶ", "(is) all right; OK", 5),
    Readings("今", "いま ", "now", 5),
    Readings("今日", "き ょう", "today", 5),
    Readings("食べる", "た ", "(to) eat", 5),
    Readings("見る", "み ", "(to) see", 5),
    Readings("行く", "い ", "(to) go", 5),
    Readings("自分", "じ ぶん", "oneself", 5),
    Readings("写真", "しゃ しん", "photo", 5),
    Readings("新しい", "あたら ", "new", 5),
    Readings("お金", " かね", "money", 5),
    Readings("来る", "く ", "coming", 5),

    Readings("いつ", "いつ", "when", 5),
    Readings("でも", "でも", "but", 5),
    Readings("じゃあ", "じゃあ", "well then; so", 5)
)

/**
 * @return true if contains hiragana or katakana
 **/
fun hasKana(word: String): Boolean {
    return word.contains(Regex("[\u3040-\u309F]")) ||  // hiragana
           word.contains(Regex("[\u30A0-\u30FF]")) // katakana
}