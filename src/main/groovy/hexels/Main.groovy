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
    List sizes = [10, 14, 22, 26, 34, 38, 46, 50, 54, 58]
    if (args.length > 2) {
        try {
            int size = Integer.parseInt(args[2])
            return size < 10 ? sizes[size] : sizes[2]
        } catch (Exception e) {
        }
    }
    return sizes[2]
}