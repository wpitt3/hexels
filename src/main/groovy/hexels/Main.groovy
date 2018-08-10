package hexels

if (args.length < 2) {
  println "requires 2 args infile and outfile"
  return
}

println "From file ${args[0]}"
println "To file ${args[1]}"

int size = parseSize(args)
println "Size $size"

List obama = IRW.convertImageToArray(IRW.readBufferedImage(args[0]))

HexelBuilder hexelBuilder = new HexelBuilder(obama.size(), obama[0].size(), size)

List hexagons = hexelBuilder.createHexagonsIndexes()
List image = hexelBuilder.createHexImage(hexagons, obama)
IRW.writeBufferedImage(IRW.convertArrayToImage(image), args[1])

int parseSize(String[] args) {
  if (args.length > 2) {
    try {
      int size = Integer.parseInt(args[2])
      return size > 0 && size % 2 == 0 ? size : 22
    } catch (Exception e) {
    }
  }
  return 22
}