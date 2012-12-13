if(args.size != 2) {
  println("I HATE YOU")
  sys.exit(1)
}

val start = args(0)
val end = args(1)

if(start.length != end.length) {
  println("IDIOT")
  sys.exit(1)
}

println(start + " > " + end)
val answer = doEverything(start, end)
println(answer)

def doEverything(start: String, end: String): String = {
  var lines = io.Source.fromFile("/usr/share/dict/words").getLines.filter(_.length == start.length).toIterable.toSet.par

  val pair = lines.partition(isOffByOne(start, _))
  var offByOne = pair._1.map(x => (x, start + " > " + x)).toMap
  lines = pair._2.toSet

  do {
    offByOne.get(end) match {
      case Some(x) => return x
      case None => None
    }

    val possibilities = offByOne.par.map(x => {
        val items = lines.filter(isOffByOne(x._1, _)).toSet
        lines = lines &~ items
        items.map(y => (y, x._2 + " > " + y))
      }).flatten.toMap

    offByOne = possibilities
  } while (lines.size > 0 && offByOne.size > 0) 

  return "FAILURE"
}

def isOffByOne(word: String, other: String) = (word, other).zipped.map(_ == _).count(!_) == 1
