/**
 * Created by willpitt on 20/01/2017.
 */
class HexelBuilder {
    Integer width, height
    Double radius
    HexagonBuilder hexagonBuilder

    HexelBuilder() {
        width = 1000
        height = 1000
        radius = 100
        hexagonBuilder = new HexagonBuilder(width, height, radius)
    }

    List createHex() {
        List image = (0..<width).collect{ x-> (0..<height).collect{y -> [0,0,0]}}

        List hexagons = hexagonBuilder.createHexagons()

        hexagons.each{ column ->
            column.each { hexagon ->
                hexagon.each { coord ->
                    image[(int)Math.round(coord[0])][(int)Math.round(coord[1])] = [255, 255, 255]
                }
            }
        }
        return image
    }
}
