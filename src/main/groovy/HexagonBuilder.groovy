/**
 * Created by willpitt on 20/01/2017.
 */
class HexagonBuilder {
    Integer width, height
    Double radius

    HexagonBuilder(Integer width, Integer height, Double radius) {
        this.width = width
        this.height = height
        this.radius = radius
    }

    List createHexagons() {
        double xOffset = xOffset()
        double yOffset = yOffset()

        double xStart = radius
        double yStart = yOffset

        Integer xSize = numberOfColumns(xOffset)
        Integer ySize = numberOfRows(yOffset)

        return (0..<xSize).collect{ xIndex ->
            (0..<ySize).collect { yIndex ->
                createShape((xStart + xIndex*xOffset), yStart + yIndex*yOffset*2 + (xIndex%2==1?yOffset:0) , 6)
            }
        }
    }

    private Double xOffset() {
        return radius + radius * Math.sin(Math.toRadians(30))
    }

    private Double yOffset() {
        return radius * Math.sin(Math.toRadians(60))
    }

    private Integer numberOfColumns(Double xOffset) {
        return Math.ceil((width - 1 - (radius * 2)) / xOffset)
    }

    private Integer numberOfRows(Double yOffset) {
        return Math.ceil((height - 1 - yOffset * 3) / (yOffset*2))
    }

    private List createShape(Double centreX, Double centreY, Integer edges) {
        double angle = Math.toRadians(360/edges)
        (0..<edges).collect { Integer deg ->
            [centreX + Math.cos(angle*deg) * radius, centreY + Math.sin(angle*deg) * radius ]
        }
    }
}
