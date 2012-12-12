val start = "dad"
val end = "zip"
var current = start
val lines = io.Source.fromFile("wlist_match1.txt").getLines.filter(_.length == start.length).toIterable

val checked = new collection.immutable.HashSet[String]

def isOffByOne(word: String, other: String): Boolean = (word, other).zipped.map(_ == _).count(!_) == 1
