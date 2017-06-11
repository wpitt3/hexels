import spock.lang.Specification

/**
 * Created by willpitt on 07/06/2017.
 */
class HexelsTest extends Specification{
    Double size, xPos, yPos, delta
    Integer edges
    HexelBuilder hexelBuilder

    void setup() {
        size = 20
        xPos = 100
        yPos = 100
        delta = 0.001
        edges = 6
        hexelBuilder = new HexelBuilder([width:1000, height:1000, radius:100])
    }

    void "all vertices are 'size' distance from centre"() {
        when:
        List<List> hexPoints = hexelBuilder.createShape(size, xPos, yPos, edges)
        then:
        hexPoints.every { point ->
            Double xDiff = (point[0]-xPos)**2
            Double yDiff = (point[1]-yPos)**2
            (xDiff + yDiff) - size**2 < delta
        }
    }

    void "all vertices are 'size' distance from next point"() {
        when:
        List<List> hexPoints = hexelBuilder.createShape(size, xPos, yPos, edges)
        then:
        (0..<hexPoints.size()).every { index ->
            List nextPoint = hexPoints[(index+1)%hexPoints.size()]
            Double xDiff = (hexPoints[index][0]-nextPoint[0])**2
            Double yDiff = (hexPoints[index][1]-nextPoint[1])**2
            (xDiff + yDiff) - size**2 < delta
        }
    }

    void "hexagon has horizontal lines on top bottom and middle"() {
        when:
        List<List> hexPoints = hexelBuilder.createShape(size, xPos, yPos, edges)
        then:
        hexPoints[0][1] - hexPoints[3][1] < delta // across middle
        hexPoints[1][1] - hexPoints[2][1] < delta // bottom
        hexPoints[4][1] - hexPoints[5][1] < delta // top
    }

    void ""() {
        when:
        List hexes = hexelBuilder.createHexagons()
        then:
        println hexes.size()
        println hexes[0].size()
    }
}
