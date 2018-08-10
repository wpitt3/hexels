package hexels

import spock.lang.Specification

/**
 * Created by willpitt on 07/06/2017.
 */
class HexagonBuilderSpec extends Specification{
    Double xPos, yPos, delta, radius
    Integer edges, width, height
    hexels.HexagonBuilder hexagonBuilder

    void setup() {
        xPos = 100
        yPos = 100
        delta = 0.001
        edges = 6
        width = 1000
        height = 1000
        radius = 100
        hexagonBuilder = new hexels.HexagonBuilder(width, height, radius)
    }

    void "all vertices are 'size' distance from centre"() {
        when:
        List<List> hexPoints = hexagonBuilder.createShape(xPos, yPos, edges)
        then:
        hexPoints.every { point ->
            Double xDiff = (point[0]-xPos)**2
            Double yDiff = (point[1]-yPos)**2
            (xDiff + yDiff) - radius**2 < delta
        }
    }

    void "all vertices are 'size' distance from next point"() {
        when:
        List<List> hexPoints = hexagonBuilder.createShape(xPos, yPos, edges)
        then:
        (0..<hexPoints.size()).every { index ->
            List nextPoint = hexPoints[(index+1)%hexPoints.size()]
            Double xDiff = (hexPoints[index][0]-nextPoint[0])**2
            Double yDiff = (hexPoints[index][1]-nextPoint[1])**2
            (xDiff + yDiff) - radius**2 < delta
        }
    }

    void "hexagon has horizontal lines on top bottom and middle"() {
        when:
        List<List> hexPoints = hexagonBuilder.createShape(xPos, yPos, edges)
        then:
        hexPoints[0][1] - hexPoints[3][1] < delta // across middle
        hexPoints[1][1] - hexPoints[2][1] < delta // bottom
        hexPoints[4][1] - hexPoints[5][1] < delta // top
    }

    void "calculate number of columns fits exactly"() {
        when:
        hexagonBuilder.radius = 101
        hexagonBuilder.width = 961
        Integer numberOfColumns = hexagonBuilder.numberOfColumns(hexagonBuilder.xOffset())
        then:
        assert numberOfColumns == 6
    }

    void "calculate number of columns one pixel too few"() {
        when:
        hexagonBuilder.radius = 101
        hexagonBuilder.width = 960
        Integer numberOfColumns = hexagonBuilder.numberOfColumns(hexagonBuilder.xOffset())
        then:
        assert numberOfColumns == 5
    }

    void "calculate number of rows fits exactly"() {
        when:
        hexagonBuilder.height = 954
        Integer numberOfRows = hexagonBuilder.numberOfRows(hexagonBuilder.yOffset())
        then:
        assert numberOfRows == 5
    }

    void "calculate number of rows fone pixel too few"() {
        when:
        hexagonBuilder.height = 953
        Integer numberOfRows = hexagonBuilder.numberOfRows(hexagonBuilder.yOffset())
        then:
        assert numberOfRows == 4
    }

    void "create 2*2 grid"() {
        when:
        hexagonBuilder.width = 500
        hexagonBuilder.height = 500
        List hexagons = hexagonBuilder.calculateHexagons(hexagonBuilder.calculateHexagonCentres())
        then:
        pointsAreEqual(hexagons[0][0][0], hexagons[1][0][4])
        pointsAreEqual(hexagons[0][0][1], hexagons[1][0][3]) && pointsAreEqual(hexagons[0][0][1], hexagons[0][1][5])
        pointsAreEqual(hexagons[0][0][2], hexagons[0][1][4])

        pointsAreEqual(hexagons[1][1][3], hexagons[0][1][1])
        pointsAreEqual(hexagons[1][1][4], hexagons[0][1][0]) && pointsAreEqual(hexagons[1][1][4], hexagons[1][0][2])
        pointsAreEqual(hexagons[1][1][5], hexagons[1][0][1])
    }

    private Boolean pointsAreEqual(List point1, List point2) {
        return (0..1).every{Math.abs(point1[it] - point2[it]) < delta}
    }
}
