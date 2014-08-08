package fr.mistertie.lawnmower.core.mower

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.lawn.Lawn
import fr.mistertie.lawnmower.core.point.Point
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.flatspec.{IntegrationTest, UnitTest}

/**
 * Test cases about [[Mower]].
 * @see [[BaseSpec]]
 */
class MowerSpec extends BaseSpec {

  "A mower" must "not be created on already booked position" taggedAs UnitTest in {
    an[IllegalArgumentException] should be thrownBy {
      // Arrange
      val parentLawn = Lawn(1, 1)
      Mower(Point(0, 0), NORTH, parentLawn)

      // Act
      Mower(Point(0, 0), SOUTH, parentLawn)

    }
  }

  it should "be created on free position" taggedAs UnitTest in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialPosition = Point(0, 0)

    // Act
    Mower(initialPosition, NORTH, parentLawn)

    // Assert
    parentLawn.isBookedAt(initialPosition) should equal(true)
  }

  "A mower oriented to the north" should "be oriented to the west after a left rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), NORTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(WEST)
  }

  it should "be oriented to the east after a right rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), NORTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(EAST)
  }

  "A mower oriented to the east" should "be oriented to the north after a left rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), EAST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(NORTH)
  }

  it should "be oriented to the south after a right rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), EAST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(SOUTH)
  }

  "A mower oriented to the south" should "be oriented to the east after a left rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), SOUTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(EAST)
  }

  it should "be oriented to west after a right rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), SOUTH, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(WEST)
  }

  "A mower oriented to the west" should "be oriented to the south after a left rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), WEST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionLeft()

    // Assert
    testedObject.orientation should equal(SOUTH)
  }

  it should "be oriented to the north after a right rotation" taggedAs IntegrationTest in {
    // Arrange
    val testedObject = Mower(Point(0, 0), WEST, Lawn(1, 1))

    // Act
    testedObject.rotatePositionRight()

    // Assert
    testedObject.orientation should equal(NORTH)
  }
}
