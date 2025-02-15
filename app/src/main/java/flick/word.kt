package flick

data class Readings(
    /** written in kana form **/
    val kana: String,
    /** written in kanji form  **/
    val kanji: String,
    /** display in furigana (above the kanji  **/
    val furigana: String,
    /** meaning in english  **/
    val english: String,
    /** word difficulty  **/
    val jpl: UByte = 0u
)

val word = arrayOf(
    Readings("だいじょうぶ", "大丈夫", "だい じょう ぶ", "(is) all right; OK", 5u),
    Readings("いま", "今", "いま ", "now", 5u),
    Readings("きょう", "今日", "き ょう", "today", 5u),
    Readings("たべる", "食べる", "た ", "(to) eat", 5u),
    Readings("みる", "見る", "み ", "(to) see", 5u),
    Readings("いく", "行く", "い ", "(to) go", 5u),
    Readings("じぶん", "自分", "じ ぶん", "oneself", 5u),
    Readings("しゃしん", "写真", "しゃ しん", "photo", 5u),
    Readings("あたらしい", "新しい", "あたら ", "new", 5u),
    Readings("おかね", "お金", "お かね", "money", 5u),
    Readings("くる", "来る", "く ", "coming", 5u),
    Readings("もんだい", "問題", "もん だい", "question; problem", 5u),
    Readings("せんせい", "先生", "せん せい", "teacher", 5u),
    Readings("しごと", "仕事", "し ごと", "work", 5u),
    Readings("かいしゃ", "会社", "かい しゃ", "workplace", 5u),
    Readings("いろ", "色", "いろ ", "color", 5u),
    Readings("あか", "赤", "あか ", "red", 5u),
    Readings("みどり", "緑", "みどり ", "green", 5u),
    Readings("あお", "青", "あお ", "blue; green", 5u),
    Readings("おとこ", "男", "おとこ ", "male", 5u),
    Readings("おんな", "女", "おんな ", "female", 5u),
    Readings("こ", "子", "こ ", "young; child", 5u),
    Readings("め", "目", "め ", "(body part) eye", 5u),
    Readings("て", "手", "て ", "(body part) hand", 5u),
    Readings("あし", "足", "あし ", "(body part) leg", 5u),

    Readings("いつ", "いつ", "", "when", 5u),
    Readings("でも", "でも", "", "but", 5u),
    Readings("じゃあ", "じゃあ", "", "well then; so", 5u)
)

/**
 * @return true if contains hiragana or katakana
 **/
fun hasKana(word: String): Boolean {
    return word.contains(Regex("[\u3040-\u309F]")) ||  // hiragana
           word.contains(Regex("[\u30A0-\u30FF]")) // katakana
}

/**
 * @return true if contains kanji
 **/
fun hasKanji(word: String): Boolean {
    return word.contains(Regex("[\u4E00-\u9FFF]"))
}