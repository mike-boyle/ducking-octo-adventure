val start = "dad"
val end = "zip"
val lines = io.Source.fromFile("wlist_match1.txt").getLines.filter(_.length == start.length).toIterable
print(lines.head)

