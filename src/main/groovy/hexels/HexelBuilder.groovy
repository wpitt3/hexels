package hexels
/**
 * Created by willpitt on 20/01/2017.
 */
class HexelBuilder {
  Integer width, height
  Double radius
  HexagonBuilder hexagonBuilder

  HexelBuilder(Integer width = 1000, Integer height = 1000, Double radius = 10) {
    this.width = width
    this.height = height
    this.radius = radius
    hexagonBuilder = new HexagonBuilder(width, height, radius)
  }

  List createHexagonsIndexes() {
    List hexagons = (0..<width).collect { x -> (0..<height).collect { y -> -1 } }
    List hexagonCentres = hexagonBuilder.calculateHexagonCentres()
    hexagonCentres.eachWithIndex { column, xIndex ->
      column.eachWithIndex { hexagonCentre, yIndex ->
        Integer hexIndex = hexagonCentres[0].size() * xIndex + yIndex
        List hexagon = hexagonBuilder.createShape(hexagonCentre[0], hexagonCentre[1], 6)
            .collect { point -> point.collect { (int) Math.round(it) } }
        [0, 2, 3, 5].each { pointIndex ->
          findAllPointsCloserToCentreThanLine(hexagon[pointIndex], hexagon[(pointIndex + 1) % 6], hexagonCentre).each { x, ys ->
            ys.each { y -> hexagons[x][y] = hexIndex }
          }
        }
        (hexagon[4][0]..hexagon[1][0]).each { x ->
          (hexagon[4][1]..<hexagon[1][1]).each { y -> hexagons[x][y] = hexIndex }
        }
      }
    }
    return hexagons
  }

  List createHexImage(List hexagons, List originalImage) {
    List image = (0..<width).collect { x -> (0..<height).collect { y -> [0, 0, 0] } }
    List hexFrequency = (0..1000000).collect { 0 }
    List hexColour = (0..1000000).collect { [0, 0, 0] }
    (0..<width).each { x ->
      (0..<height).each { y ->
        if (hexagons[x][y] >= 0) {
          hexFrequency[hexagons[x][y]] += 1
          (0..<3).each {
            hexColour[hexagons[x][y]][it] += originalImage[x][y][it]
          }
        }
      }
    }
    hexFrequency.eachWithIndex { count, index ->
      if (count > 0) {
        hexColour[index] = hexColour[index].collect { (int) Math.min(255, Math.round(it / count)) }
      }
    }
    (0..<width).each { x ->
      (0..<height).each { y ->
        if (hexagons[x][y] >= 0) {
          image[x][y][0] = hexColour[hexagons[x][y]][0]
          image[x][y][1] = hexColour[hexagons[x][y]][1]
          image[x][y][2] = hexColour[hexagons[x][y]][2]
        }
      }
    }
    return image
  }

  Map findAllPointsCloserToCentreThanLine(List hexagonA, List hexagonB, List hexagonCentre) {
    Double xProduct = crossProduct(hexagonA, hexagonB, hexagonCentre[0], hexagonCentre[1])
    (hexagonA[0]..hexagonB[0]).collectEntries { x ->
      [(x): (hexagonA[1]..hexagonB[1]).findAll { y ->
        return crossProduct(hexagonA, hexagonB, x, y) * xProduct > -0.0001
      }]
    }
  }

  Double crossProduct(List pointA, List pointB, Double x, Double y) {
    return (x - pointA[0]) * (pointB[1] - pointA[1]) - (y - pointA[1]) * (pointB[0] - pointA[0])
  }
}
