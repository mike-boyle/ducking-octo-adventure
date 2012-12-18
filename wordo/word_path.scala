require(args.size == 2, "Invalid number of arguments")
val start = args(0)
val end = args(1)
require(start.length == end.length, "Word lengths must match.")

val lines = io.Source.fromFile("/usr/share/dict/words").getLines filter { _.length == start.length }
var dictionary = lines.toSet.par

requireWordInDictionary(start)
requireWordInDictionary(end)

println(start + " > " + end)
println(getAnswer())

def requireWordInDictionary(word: String) = require(dictionary.contains(word), word + " not in dictionary")

def getAnswer() = {
  val stream = Iterator.iterate(Map(start -> start)) { getNeighbors }
  val lastElement = stream.find { isLastElement }
  lastElement.get.getOrElse(end, "FAILURE")
}

def isLastElement(x: Map[String, String]) = x.contains(end) || x.isEmpty

def isOffByOne(word: String, other: String) = {
  val chars = (word, other).zipped
  val offCount = chars map { _ == _ } count { !_ }
  offCount == 1
}

def getNeighbors(words: Map[String, String]) = {
  words flatMap { x =>  
    val (neighbors, others) = dictionary partition { isOffByOne(x._1, _) }
    dictionary = others
    neighbors map { y => (y, x._2 + " > " + y) }
  }
}

