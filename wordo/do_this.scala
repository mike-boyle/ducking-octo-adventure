val start = "catch"
val end = "blast"

time({
  println(start + " > " + end)
  val answer = doEverything(start, end)
  println(answer)
})

def doEverything(start: String, end: String): String = {
  var lines = io.Source.fromFile("wlist_match1.txt").getLines.filter(_.length == start.length).toIterable.toSet.par

  val pair = lines.partition(isOffByOne(start, _))
  var offByOne = pair._1.map(x => (x, start + " > " + x)).toMap
  lines = pair._2.toSet

  while(lines.size > 0 && offByOne.size > 0) { 
    if (offByOne.contains(end)) {
      return offByOne.get(end).get
    }

    val possibilities = offByOne.par.map(x => {
        val items = lines.filter(isOffByOne(x._1, _)).toSet
        lines = lines &~ items
        items.map(y => (y, x._2 + " > " + y))
      }).flatten.toMap

    offByOne = possibilities
  }

  return "FAILURE"
}

def isOffByOne(word: String, other: String): Boolean = (word, other).zipped.map(_ == _).count(!_) == 1

def time[A](a: => A) = {
        val now = System.nanoTime
        val result = a
        val seconds = (System.nanoTime - now) / 1000000000
        println("%d seconds".format(seconds))
        result
      }
