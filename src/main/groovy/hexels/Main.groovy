package hexels

if (args.length < 2) {
    println "requires 2 args infile and outfile"
    return
}

println "From file ${args[0]}"
println "To file ${args[1]}"

List obama = IRW.convertImageToArray(IRW.readBufferedImage(args[0]))
HexelBuilder hexelBuilder = new HexelBuilder(obama.size(), obama[0].size())

List hexagons = hexelBuilder.createHexagonsIndexes()
List image = hexelBuilder.createHexImage(hexagons, obama)
IRW.writeBufferedImage(IRW.convertArrayToImage(image), args[1])