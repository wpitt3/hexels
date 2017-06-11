/**
 * Created by willpitt on 20/01/2017.
 */
class HexelBuilder {
    Double width, height, radius

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
            }
        }
        return image
    }

    private List createHexagons() {
        double xOffset = calculateXOffset()
        double yOffset = calculateYOffset()

        Integer xSize = calculateNoOfColumns(xOffset)
        Integer ySize = calculateNoOfRows(yOffset)

        double xStart = radius
        double yStart = yOffset

        return (0..<xSize).collect{ xIndex ->
            (0..<ySize).collect { yIndex ->
                createShape(radius, (xStart + xIndex*xOffset), yStart + yIndex*yOffset*2 + (xIndex%2==1?yOffset:0) , 6)
            }
        }
    }

    private Double calculateXOffset() {
        return radius + radius * Math.sin(Math.toRadians(30))
    }

    private Double calculateYOffset() {
        return radius * Math.sin(Math.toRadians(60))
    }

    private Integer calculateNoOfColumns(Double xOffset) {
        return Math.ceil((width - 1 - (radius * 2)) / xOffset)
    }

    private Integer calculateNoOfRows(Double yOffset) {
        return Math.ceil((height - 1 - yOffset * 3) / (yOffset*2))
    }

    private List createShape(Double size, Double centreX, Double centreY, Integer edges) {
        double angle = Math.toRadians(360/edges)
        (0..<edges).collect { Integer deg ->
            [centreX + Math.cos(angle*deg) * size, centreY + Math.sin(angle*deg) * size]
        }
    }
}
