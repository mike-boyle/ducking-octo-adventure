val start = "flat"
val end = "dope"
var lines = io.Source.fromFile("wlist_match1.txt").getLines.filter(_.length == start.length).toIterable.toSet

val pair = lines.partition(isOffByOne(start, _))
var offByOne = pair._1.map(x => (x, new collection.mutable.Stack() + x)).toMap
lines = pair._2.toSet
var distance = 1;

while(lines.size > 0 && offByOne.size > 0) { 
  if (offByOne.contains(end)) {
    println(distance);
    println(offByOne.get(end).reduce(_ + " m>m " + _))
    sys.exit
  }
  else {
    distance = distance + 1
  }

  println("offByOneSize: " + offByOne.size)

  val possibilities = offByOne.map(x => {
      val items = lines.filter(isOffByOne(x._1, _)).toSet
      lines = lines &~ items
      items.map(y => (y, x._2 + y))
    }).flatten.toMap

  println("possibiliesSize: " + possibilities.size)
  println("linesSize: " + lines.size)
  lines = lines &~ possibilities.keys.toSet

  offByOne = possibilities
}

println("FAILURE")

def isOffByOne(word: String, other: String): Boolean = (word, other).zipped.map(_ == _).count(!_) == 1
