package hexels

import spock.lang.Specification

/**
 * Created by willpitt on 07/06/2017.
 */
class HexelBuilderSpec extends Specification {
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
      List hexFrequencies = (0..8).collect { 0 }
    then:
      hexagonIndexes != null
      8 == hexagonIndexes.collect { it.max() }.max()
      hexagonIndexes.each { column -> column.findAll { it >= 0 }.each { hexFrequencies[it]++ } }
      (5..45).every { x ->
        (9..51).every { y ->
          hexagonIndexes[x][y] != -1
        }
      }
  }

//  void "properly tesalate"() {
//    given:
//      HexelBuilder hexelBuilder = new HexelBuilder(44, 55, 12)
//
//    when:
//      List hexagons = hexelBuilder.createHexagonsIndexes()
//
//    then:
//      (0..<hexagons[0].size()).collect{ y ->
//        (0..<hexagons.size()).collect { x ->
//           hexagons[x][y] + 1
//        }
//      }.each {
//        println it
//      }
//    // 21, 26
//    //[24, 31]
//    //[18, 21]
//      true
//  }

//    void "find points inside hexagon"() {
//        when :
//            List hexagon = [[20, 9], [15, 17], [5, 17], [0, 9], [5, 0], [15, 0]]
//            List hexagonCentre = [10.0, 8.660254037844386]
//            List result = [0, 2, 3, 5].collect { pointIndex ->
//                hexelBuilder.findAllPointsCloserToCentreThanLine(hexagon[pointIndex], hexagon[(pointIndex + 1) % 6], hexagonCentre)
//            }
//        then:
//            result[0] == [19:[9, 10], 18:[9, 10, 11, 12], 17:[9, 10, 11, 12, 13], 16:[9, 10, 11, 12, 13, 14, 15], 15:[9, 10, 11, 12, 13, 14, 15, 16]]
//            result[1] == [5:[16, 15, 14, 13, 12, 11, 10, 9], 4:[15, 14, 13, 12, 11, 10, 9], 3:[13, 12, 11, 10, 9], 2:[12, 11, 10, 9], 1:[10, 9]]
//            result[2] == [1:[9, 8], 2:[9, 8, 7, 6], 3:[9, 8, 7, 6, 5, 4], 4:[9, 8, 7, 6, 5, 4, 3, 2], 5:[9, 8, 7, 6, 5, 4, 3, 2, 1]]
//            result[3] == [15:[1, 2, 3, 4, 5, 6, 7, 8, 9], 16:[2, 3, 4, 5, 6, 7, 8, 9], 17:[4, 5, 6, 7, 8, 9], 18:[6, 7, 8, 9], 19:[8, 9]]
//    }

  private Boolean pointsAreEqual(List point1, List point2) {
    return (0..1).every { Math.abs(point1[it] - point2[it]) < delta }
  }
}
