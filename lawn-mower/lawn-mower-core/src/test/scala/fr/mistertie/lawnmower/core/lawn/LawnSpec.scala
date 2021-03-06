package fr.mistertie.lawnmower.core.lawn

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.point.Point
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.UnitTest

/**
 * Test cases about [[Lawn]].
 * @see [[BaseSpec]].
 */
@UnitTest
class LawnSpec extends BaseSpec {

  "A lawn without point" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(0, 0)
    } should have message "A lawn should have at least one point."
  }

  "A lawn with negative top right corner's coordinate" should "produce an IllegalArgument Exception" in {
    val invalidCoordinates = Table(("abscissa", "ordinate"), (-4, 12), (12, -4), (-4, -4))

    forAll(invalidCoordinates) { (abscissa: Int, ordinate: Int) =>
      the[IllegalArgumentException] thrownBy {
        Lawn(abscissa, ordinate)
      } should have message "A lawn can't have top right corner with negative coordinate."
    }
  }

  "A lawn" should "not book the next north position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(0, 0), NORTH)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, NORTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(currentPosition) should equal(true)
    testedObject.isBooked(Point(0, 2)) should equal(false)
  }

  it should "not book the next east position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(0, 0), EAST)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, EAST)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(currentPosition) should equal(true)
    testedObject.isBooked(Point(2, 0)) should equal(false)
  }

  it should "not book the next south position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(1, 1), SOUTH)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, SOUTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(currentPosition) should equal(true)
    testedObject.isBooked(Point(1, -1)) should equal(false)
  }

  it should "not book the next west position if this one is out of bound" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val currentPosition = testedObject.checkAndBookNextPosition(Point(1, 1), WEST)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, WEST)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(currentPosition) should equal(true)
    testedObject.isBooked(Point(-1, 1)) should equal(false)
  }

  it should "not book the next north position if this one is taken" in {
    // Arrange
    val bookedPosition = Point(1, 1)
    val currentPosition = Point(1, 0)
    val testedObject = Lawn(1, 1)

    testedObject.initializePosition(currentPosition)
    testedObject.initializePosition(bookedPosition)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, NORTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(bookedPosition) should equal(true)
    testedObject.isBooked(currentPosition) should equal(true)
  }

  it should "not book the next east position if this one is taken" in {
    // Arrange
    val bookedPosition = Point(2, 0)
    val currentPosition = Point(1, 0)
    val testedObject = Lawn(2, 2)

    testedObject.initializePosition(bookedPosition)
    testedObject.initializePosition(currentPosition)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, EAST)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(bookedPosition) should equal(true)
    testedObject.isBooked(currentPosition) should equal(true)
  }

  it should "not book the next south position if this one is taken" in {
    // Arrange
    val bookedPosition = Point(1, 0)
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    testedObject.initializePosition(bookedPosition)
    testedObject.initializePosition(currentPosition)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, SOUTH)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(bookedPosition) should equal(true)
    testedObject.isBooked(currentPosition) should equal(true)
  }

  it should "not book the next west position if this one is taken" in {
    // Arrange
    val bookedPosition = Point(0, 0)
    val currentPosition = Point(1, 0)
    val testedObject = Lawn(1, 1)

    testedObject.initializePosition(bookedPosition)
    testedObject.initializePosition(currentPosition)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, WEST)

    // Assert
    resultingPosition should equal(currentPosition)
    testedObject.isBooked(bookedPosition) should equal(true)
    testedObject.isBooked(currentPosition) should equal(true)
  }

  it should "book the next north position if this one is valid and release the current one" in {
    // Arrange
    val currentPosition = Point(1, 0)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, NORTH)

    // Assert
    resultingPosition should equal(Point(1, 1))
    testedObject.isBooked(currentPosition) should equal(false)
    testedObject.isBooked(resultingPosition) should equal(true)
  }

  it should "book the next east position if this one is valid and release the current one" in {
    // Arrange
    val currentPosition = Point(0, 1)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, EAST)

    // Assert
    resultingPosition should equal(Point(1, 1))
    testedObject.isBooked(currentPosition) should equal(false)
    testedObject.isBooked(resultingPosition) should equal(true)
  }

  it should "book the next south position if this one is valid and release the current one" in {
    // Arrange
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, SOUTH)

    // Assert
    resultingPosition should equal(Point(1, 0))
    testedObject.isBooked(currentPosition) should equal(false)
    testedObject.isBooked(resultingPosition) should equal(true)
  }

  it should "book the next west position if this one is valid and released the current one" in {
    // Arrange
    val currentPosition = Point(1, 1)
    val testedObject = Lawn(1, 1)

    // Act
    val resultingPosition = testedObject.checkAndBookNextPosition(currentPosition, WEST)

    // Assert
    resultingPosition should equal(Point(0, 1))
    testedObject.isBooked(currentPosition) should equal(false)
    testedObject.isBooked(resultingPosition) should equal(true)
  }

  it should "declare the given position as booked if this one is not free" in {
    // Arrange
    val testedObject = Lawn(1, 1)
    val bookedPosition = testedObject.checkAndBookNextPosition(Point(0, 0), NORTH)

    // Act
    val result = testedObject.isBooked(bookedPosition)

    // Assert
    result should equal(true)
  }

  it should "declare the given position as free if this one is not booked" in {
    // Arrange
    val testedObject = Lawn(1, 1)

    // Act
    val result = testedObject.isBooked(Point(0, 0))

    // Assert
    result should equal(false)
  }

  it should "produce an IllegalArgumentException while initializing an already booked position" in {
    the[IllegalArgumentException] thrownBy {
      // Arrange
      val testedObject = Lawn(1, 1)
      val positionToInitialize = Point(0, 0)
      testedObject.initializePosition(positionToInitialize)

      // Act
      testedObject.initializePosition(positionToInitialize)

    } should have message "Position already booked: Point(0,0)"
  }

  it should "produce an IllegalArgumentException while initializing an out of bound position" in {
    val invalidPositions = Table(("abscissa", "ordinate"), (2, 1), (1, 2), (0, -1), (-1, 0))

    forAll(invalidPositions) { (abscissa: Int, ordinate: Int) =>
      the[IllegalArgumentException] thrownBy {
        // Arrange
        val testedObject = Lawn(1, 1)

        // Act
        testedObject.initializePosition(Point(abscissa, ordinate))
      } should have message "Position out of bounds: Point(%s,%s)".format(abscissa, ordinate)
    }
  }

  it should "initialize the given position if this one is free" in {
    // Arrange
    val positionToInitialize = Point(0, 0)
    val testedObject = Lawn(1, 1)

    // Act
    testedObject.initializePosition(positionToInitialize)

    // Assert
    testedObject.isBooked(positionToInitialize) should equal(true)
  }
}
