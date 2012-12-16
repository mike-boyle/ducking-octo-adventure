if(args.size != 2) {
  println("Invalid number of arguments.")
  sys.exit(1)
}

val start = args(0)
val end = args(1)

if(start.length != end.length) {
  println("Word lengths must match.")
  sys.exit(1)
}

var lines = io.Source.fromFile("/usr/share/dict/words").getLines
  .filter(_.length == start.length).toIterable.toSet

if(!lines.contains(start) || !lines.contains(end)) {
  println("Word does not exist in dictionary.")
  sys.exit(1)
}

println(start + " > " + end)
println(getAnswer)

def getAnswer: String = {
  val stream = collection.Iterator.iterate(Map(start -> start))(getNeighbors)
  stream.find(isLastElement).get.getOrElse(end, "FAILURE")
}

def isLastElement(x: Map[String, String]) = x.contains(end) || x.isEmpty
def isOffByOne(word: String, other: String) = (word, other).zipped.map(_ == _).count(!_) == 1

def getNeighbors(words: Map[String, String]): Map[String, String] = {
  words.map(x => {
    val items = lines.filter(isOffByOne(x._1, _)).toSet
    lines = lines &~ items
    items.map(y => (y, x._2 + " > " + y))
  }).flatten.toMap
}

