package fr.mistertie.lawnmower.core.mower

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.lawn.Lawn
import fr.mistertie.lawnmower.core.point.Point
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.IntegrationTest

/**
 * Test cases about [[Mower]].
 * @see [[BaseSpec]]
 */
@IntegrationTest
class MowerSpec extends BaseSpec {

  "A mower" must "not be created on already booked position" in {
    an[IllegalArgumentException] should be thrownBy {
      // Arrange
      val parentLawn = Lawn(1, 1)
      Mower(Point(0, 0), NORTH, parentLawn)

      // Act
      Mower(Point(0, 0), SOUTH, parentLawn)

    }
  }

  it should "be created on free position" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialPosition = Point(0, 0)

    // Act
    Mower(initialPosition, NORTH, parentLawn)

    // Assert
    parentLawn.isBookedAt(initialPosition) should equal(true)
  }

  it should "ignore invalid actions during exploring" in {
    // Arrange
    val initialOrientation = NORTH
    val initialPosition = Point(0, 0)
    val parentLawn = Lawn(1, 1)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    // Act
    testedObject.exploreLawn(List('T', 'Q', 'P'))

    // Assert
    testedObject.orientation should equal(initialOrientation)
    testedObject.position should equal(initialPosition)
  }

  it should "conserve its orientation after 4 rotations by 90 degree to the left" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialOrientation = NORTH
    val initialPosition = Point(0, 0)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    // Act
    testedObject.exploreLawn(List('G', 'G', 'G', 'G'))

    // Assert
    testedObject.orientation should equal(initialOrientation)
    testedObject.position should equal(initialPosition)
  }

  it should "conserve its orientation after 4 rotations by 90 degree to the right" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialOrientation = NORTH
    val initialPosition = Point(0, 0)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    // Act
    testedObject.exploreLawn(List('D', 'D', 'D', 'D'))

    // Assert
    testedObject.orientation should equal(initialOrientation)
    testedObject.position should equal(initialPosition)
  }

  it should "conserve its position after exploration attempts out of south and west bounds" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialOrientation = SOUTH
    val initialPosition = Point(0, 0)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)


    // Act
    testedObject.exploreLawn(List('A', 'D', 'A'))

    // Assert
    testedObject.orientation should equal(WEST)
    testedObject.position should equal(initialPosition)
  }

  it should "conserve its position after exploration attempts out of east and north bounds" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialOrientation = NORTH
    val initialPosition = Point(1, 1)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    // Act
    testedObject.exploreLawn(List('A', 'D', 'A'))

    // Assert
    testedObject.orientation should equal(EAST)
    testedObject.position should equal(initialPosition)
  }

  it should "conserve its position after exploration attempts on booked south and west points" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialOrientation = SOUTH
    val initialPosition = Point(1, 1)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    Mower(Point(0, 1), NORTH, parentLawn)
    Mower(Point(1, 0), NORTH, parentLawn)


    // Act
    testedObject.exploreLawn(List('A', 'D', 'A'))

    // Assert
    testedObject.orientation should equal(WEST)
    testedObject.position should equal(initialPosition)

  }

  it should "conserve its position after exploration attempts on booked east and north points" in {
    // Arrange
    val parentLawn = Lawn(1, 1)
    val initialOrientation = NORTH
    val initialPosition = Point(0, 0)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    Mower(Point(0, 1), NORTH, parentLawn)
    Mower(Point(1, 0), NORTH, parentLawn)


    // Act
    testedObject.exploreLawn(List('A', 'D', 'A'))

    // Assert
    testedObject.orientation should equal(EAST)
    testedObject.position should equal(initialPosition)
  }

  it should "explore a lawn without booked positions, staying inside its bounds" in {
    // Arrange
    val parentLawn = Lawn(5, 5)
    val initialOrientation = NORTH
    val initialPosition = Point(1, 2)
    val testedObject = Mower(initialPosition, initialOrientation, parentLawn)

    // Act
    testedObject.exploreLawn(List('G', 'A', 'G', 'A', 'G', 'A', 'G', 'A', 'A'))

    // Assert
    testedObject.orientation should equal(NORTH)
    testedObject.position should equal(Point(1, 3))
  }
}
