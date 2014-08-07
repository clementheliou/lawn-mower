package fr.mistertie.lawnmower.core.lawn

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.point.Point
import fr.mistertie.lawnmower.core.test.UnitSpec

/**
 * Test cases about [[Lawn]].
 * @see [[UnitSpec]].
 */
class LawnSpec extends UnitSpec {

  "A lawn without point" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(0, 0)
    } should have message "A lawn should have at least one point."
  }

  "A lawn with negative top right corner's abscissa" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(-4, 12)
    } should have message "A lawn can't have top right corner with negative coordinate."
  }

  "A lawn with negative top right corner's ordinate" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(12, -4)
    } should have message "A lawn can't have top right corner with negative coordinate."
  }

  "A lawn with negatives top right corner's coordinates" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(-4, -4)
    } should have message "A lawn can't have top right corner with negative coordinate."
  }

  "A lawn" should "not book the next north position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(0, 0), NORTH)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, NORTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBookedAt(currentPosition) should equal(true)
    testedObject.isBookedAt(Point(0, 2)) should equal(false)
  }

  it should "not book the next east position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(0, 0), EAST)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, EAST)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBookedAt(currentPosition) should equal(true)
    testedObject.isBookedAt(Point(2, 0)) should equal(false)
  }

  it should "not book the next south position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(1, 1), SOUTH)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, SOUTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBookedAt(currentPosition) should equal(true)
    testedObject.isBookedAt(Point(1, -1)) should equal(false)
  }

  it should "not book the next west position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(1, 1), WEST)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, WEST)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBookedAt(currentPosition) should equal(true)
    testedObject.isBookedAt(Point(-1, 1)) should equal(false)
  }

  it should "not book the next north position if this one is taken" in {
    // Arrange
    val currentPosition = Point(0, 0)
    val testedObject = Lawn(1, 1)

    testedObject.checkAndBookNextPosition(currentPosition, NORTH)
    testedObject.checkAndBookNextPosition(Point(0, -1), NORTH)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, NORTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBookedAt(currentPosition) should equal(true)
  }

  it should "not book the next east position if this one is taken" in {
    // Arrange
    val currentPosition = Point(0, 0)
    val testedObject = Lawn(1, 1)

    testedObject.checkAndBookNextPosition(currentPosition, EAST)
    testedObject.checkAndBookNextPosition(Point(-1, 0), EAST)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, EAST)

    // Assert
    resultingPosition should equal(currentPosition)
  }

  it should "not book the next south position if this one is taken" in {
    // Arrange
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    testedObject.checkAndBookNextPosition(currentPosition, SOUTH)
    testedObject.checkAndBookNextPosition(Point(1, 2), SOUTH)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, SOUTH)

    // Assert
    resultingPosition should equal(currentPosition)
  }

  it should "not book the next west position if this one is taken" in {
    // Arrange
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    testedObject.checkAndBookNextPosition(currentPosition, WEST)
    testedObject.checkAndBookNextPosition(Point(2, 1), WEST)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, WEST)

    // Assert
    resultingPosition should equal(currentPosition)
  }

  it should "book the next north position if this one is valid and release the current one" in {
    // Arrange
    val currentPosition = Point(1, 0)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, NORTH)

    // Assert
    resultingPosition should equal(Point(1, 1))
    testedObject.isBookedAt(currentPosition) should equal(false)
    testedObject.isBookedAt(resultingPosition) should equal(true)
  }

  it should "book the next east position if this one is valid and release the current one" in {
    // Arrange
    val currentPosition = Point(0, 1)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, EAST)

    // Assert
    resultingPosition should equal(Point(1, 1))
    testedObject.isBookedAt(currentPosition) should equal(false)
    testedObject.isBookedAt(resultingPosition) should equal(true)
  }

  it should "book the next south position if this one is valid and release the current one" in {
    // Arrange
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, SOUTH)

    // Assert
    resultingPosition should equal(Point(1, 0))
    testedObject.isBookedAt(currentPosition) should equal(false)
    testedObject.isBookedAt(resultingPosition) should equal(true)
  }

  it should "book the next west position if this one is valid and released the current one" in {
    // Arrange
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, WEST)

    // Assert
    resultingPosition should equal(Point(0, 1))
    testedObject.isBookedAt(currentPosition) should equal(false)
    testedObject.isBookedAt(resultingPosition) should equal(true)
  }

  it should "declare the given position as booked if this one is not free" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val bookedPosition = testedObject.checkAndBookNextPosition(Point(0, 0), NORTH)

    // Act
    val result = testedObject.isBookedAt(bookedPosition)

    // Assert
    result should equal(true)
  }

  it should "declare the given position as free if this one is not booked" in {
    // Arrange
    val testedObject = Lawn(1, 1)

    // Act
    val result = testedObject.isBookedAt(Point(0, 0))

    // Assert
    result should equal(false)
  }

  it should "produce an IllegalArgumentException while initialising an already booked position" in {
    the[IllegalArgumentException] thrownBy {
      // Arrange
      val testedObject = Lawn(1, 1)
      val positionToInitialise = Point(0, 0)
      testedObject.initPosition(positionToInitialise)

      // Act
      testedObject.initPosition(positionToInitialise)

    } should have message "Position already booked: Point(0,0)"
  }

  it should "initialize the given position if this one is free" in {
    // Arrange
    val positionToInitialise = Point(0, 0)
    val testedObject = Lawn(1, 1)

    // Act
    testedObject.initPosition(positionToInitialise)

    // Assert
    testedObject.isBookedAt(positionToInitialise) should equal(true)
  }
}
