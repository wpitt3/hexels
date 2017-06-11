/**
 * Created by willpitt on 20/01/2017.
 */
class HexelBuilder {
    Integer width, height, radius

    HexelBuilder() {
        width = 1000
        height = 1000
        radius = 100
    }

    List createHex() {
        List image = (0..<width).collect{ x-> (0..<height).collect{y -> [0,0,0]}}

        List hexagons = createHexagons()

        hexagons.each{ column ->
            column.each { hexagon ->
                hexagon.each { coord ->
                    image[(int)Math.round(coord[0])][(int)Math.round(coord[1])] = [255, 255, 255]
                }
                println " "
            }
        }
        return image
    }

    private Integer calculateColumns(Double xOffset, Double rowOffset) {
        return Math.ceil((width - (radius * 2 + rowOffset)) / xOffset)
    }

    private Integer calculateRows(Double yOffset) {
        return Math.ceil((height - yOffset * 2) / yOffset)
    }

    private List createShape(Double size, Double centreX, Double centreY, Integer edges) {
        double angle = Math.toRadians(360/edges)
        (0..<edges).collect { Integer deg ->
            [centreX + Math.cos(angle*deg) * size, centreY + Math.sin(angle*deg) * size]
        }
    }

    private List createHexagons() {
        double rowOffset = radius + radius * Math.sin(Math.toRadians(30))

        double xOffset = radius * 3
        double yOffset = radius * Math.sin(Math.toRadians(60))

        Integer xSize = calculateColumns(xOffset, rowOffset)
        Integer ySize = calculateRows(yOffset)

        double xStart = radius
        double yStart = yOffset

        return (0..<xSize).collect{ xIndex ->
            (0..<ySize).collect { yIndex ->
                createShape(radius, (xStart + xIndex*xOffset) + (yIndex%2==1?rowOffset:0), yStart + yIndex*yOffset, 6)
            }
        }
    }
}
