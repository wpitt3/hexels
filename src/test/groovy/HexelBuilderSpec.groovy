import spock.lang.Specification

/**
 * Created by willpitt on 07/06/2017.
 */
class HexelBuilderSpec extends Specification{
    Double delta, radius
    Integer width, height
    HexelBuilder hexelBuilder

    void setup() {
        delta = 0.001
        width = 55
        height = 70
        radius = 10
        hexelBuilder = new HexelBuilder(width, height, radius)
    }

    void "all vertices are 'size' distance from centre"() {
        when:
            List hexagonIndexes = hexelBuilder.createHexagonsIndexes()
            List hexFrequencies = (0..8).collect{0}

        then:
            hexagonIndexes != null
            8 == hexagonIndexes.collect{ it.max() }.max()
            hexagonIndexes.each{ column -> column.findAll{ it >= 0 }.each{ hexFrequencies[it]++}}
            println hexFrequencies
            (5..45).every { x ->
                (9..51).every { y ->
                    hexagonIndexes[x][y] != -1
                }
            }

    }

    private Boolean pointsAreEqual(List point1, List point2) {
        return (0..1).every{Math.abs(point1[it] - point2[it]) < delta}
    }
}
