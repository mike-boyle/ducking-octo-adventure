require(args.size == 2, "Invalid number of arguments")
val start = args(0)
val end = args(1)
require(start.length == end.length, "Word lengths must match.")

var dictionary = io.Source.fromFile("/usr/share/dict/words").getLines
  .filter(_.length == start.length).toIterable.toSet

def requireWordInDictionary(word: String) = require(dictionary.contains(word), word + " not in dictionary")
requireWordInDictionary(start)
requireWordInDictionary(end)

println(start + " > " + end)
println(getAnswer)

def getAnswer: String = {
  val stream = Iterator.iterate(Map(start -> start))(getNeighbors)
  stream.find(isLastElement).get.getOrElse(end, "FAILURE")
}

def isLastElement(x: Map[String, String]) = x.contains(end) || x.isEmpty
def isOffByOne(word: String, other: String) = (word, other).zipped.map(_ == _).count(!_) == 1

def getNeighbors(words: Map[String, String]): Map[String, String] = {
  words.map(x => {
    val items = dictionary.filter(isOffByOne(x._1, _)).toSet
    dictionary = dictionary &~ items
    items.map(y => (y, x._2 + " > " + y))
  }).flatten.toMap
}

