package fr.mistertie.lawnmower.core.mower

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.lawn.Lawn
import fr.mistertie.lawnmower.core.point.Point
import fr.mistertie.lawnmower.core.test.UnitSpec

/**
 * Test cases about [[Mower]].
 * @see [[UnitSpec]]
 */
class MowerSpec extends UnitSpec {

  "A mower oriented to the north" should "be oriented to the west after rotating to the left" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), NORTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(WEST)
  }

  it should "be oriented to the east after rotating to the right" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), NORTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(EAST)
  }

  "A mower oriented to the east" should "be oriented to the north after rotating to the left" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), EAST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(NORTH)
  }

  it should "be oriented to the south after rotating to the right" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), EAST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(SOUTH)
  }

  "A mower oriented to the south" should "be oriented to the east after rotating to the left" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), SOUTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(EAST)
  }

  it should "be oriented to west after rotating to the right" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), SOUTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(WEST)
  }

  "A mower oriented to the west" should "be oriented to the south after rotating to the left" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), WEST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(SOUTH)
  }

  it should "be oriented to the north after rotating to the right" in {
    // Arrange
    val testedObject = Mower(Point(0, 0), WEST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(NORTH)
  }
}
