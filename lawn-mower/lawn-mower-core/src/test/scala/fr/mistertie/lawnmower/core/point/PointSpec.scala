package fr.mistertie.lawnmower.core.point

import fr.mistertie.lawnmower.core.test.UnitSpec

/**
 * Test cases about [[Point]].
 * @see [[UnitSpec]].
 */
class PointSpec extends UnitSpec {

  "A point" should "return the given translation result" in {
    // Arrange
    val sourcePoint = Point(1, 2)
    val translation = Point(1, 2)

    // Act
    val resultingPoint = sourcePoint.translate(translation)

    // Assert
    resultingPoint should equal(Point(2, 4))
  }
}
