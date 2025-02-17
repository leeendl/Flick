package flick

data class Readings(
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
    Readings("大丈夫", "だい じょう ぶ", "(is) all right; OK", 5u),
    Readings("下さい", "くだ ", "please (request)", 5u),
    Readings("お願い", " ねが ", "please", 2u),
    Readings("今日", "き ょう", "today", 5u),
    Readings("先月", "せん げつ", "last month", 5u),
    Readings("食べる", "た ", "(to) eat", 5u),
    Readings("お皿", " さら", "dish", 5u),
    Readings("見る", "み ", "(to) see", 5u),
    Readings("走る", "はし ", "(to) run", 5u),
    Readings("行く", "い ", "(to) go", 5u),
    Readings("自分", "じ ぶん", "oneself", 5u),
    Readings("私", "わたし", "I", 5u),
    Readings("名前", "な まえ", "name", 5u),
    Readings("写真", "しゃ しん", "photo", 5u),
    Readings("新しい", "あたら ", "new", 5u),
    Readings("お金", " かね", "money", 5u),
    Readings("来る", "く ", "coming", 5u),
    Readings("問題", "もん だい", "question; problem", 5u),
    Readings("学校", "がっ こう", "school", 5u),
    Readings("先生", "せん せい", "teacher", 5u),
    Readings("仕事", "し ごと", "work", 5u),
    Readings("会社", "かい しゃ", "workplace", 5u),
    Readings("会議", "かい ぎ", "meeting", 4u),
    Readings("色", "いろ", "color", 5u),
    Readings("赤", "あか", "red", 5u),
    Readings("緑", "みどり", "green", 5u),
    Readings("青", "あお", "blue; green", 5u),
    Readings("男", "おとこ", "male", 5u),
    Readings("女", "おんな", "female", 5u),
    Readings("子", "こ", "young; child", 5u),
    Readings("目", "め", "(body part) eye", 5u),
    Readings("手", "て", "(body part) hand", 5u),
    Readings("足", "あし", "(body part) leg", 5u),
    Readings("馬鹿", "ば か", "idiot", 3u),
    Readings("中心", "ちゅう しん", "center; middle", 3u),
    Readings("病気", "びょう き", "illness", 5u),
    Readings("木", "き", "tree", 5u),

    Readings("", "いい", "good", 5u),
    Readings("", "いつ", "when", 5u),
    Readings("", "でも", "but", 5u),
    Readings("", "じゃあ", "well then; so", 5u)
)

fun getKana(kanji: String, spaces: List<String>): String {
    val returnList = StringBuilder(
        kanji.length + spaces.sumOf { it.takeIf { it.all { char -> char.isKana() } }?.length ?: 1}
    )
    for (i in 0 until maxOf(kanji.length, spaces.size)) {
        if (i < spaces.size) {
            if (spaces[i].isEmpty() && i < kanji.length) {
                returnList.append(kanji[i])
            }
            else if (spaces[i].all { it.isKana() }) {
                returnList.append(spaces[i])
            }
        }
        else if (i < kanji.length) {
            returnList.append(kanji[i])
        }
    }

    return returnList.toString()
}

/**
 * @return true if contains kanji
 **/
fun Char.isKanji(): Boolean = this in '\u4e00'..'\u9faf'

/**
 * @return true if contains hiragana or katakana
 **/
fun Char.isKana(): Boolean {
    return this in '\u3040'..'\u309F' ||  // hiragana
            this in '\u30A0'..'\u30FF'    // katakana
}