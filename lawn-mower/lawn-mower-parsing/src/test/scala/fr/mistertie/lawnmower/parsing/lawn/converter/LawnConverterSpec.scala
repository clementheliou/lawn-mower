package fr.mistertie.lawnmower.parsing.lawn.converter

import fr.mistertie.lawnmower.parsing.lawn.converter.LawnConverter.convert
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.flatspec.{IntegrationTest, UnitTest}
import org.scalatest.EitherValues

import scala.collection.immutable.List.{empty => emptyList}


/**
 * Test cases about [[LawnConverter]].
 */
class LawnConverterSpec extends BaseSpec with EitherValues {

  it should "return an error message if the input list has an invalid size" taggedAs UnitTest in {
    // Arrange
    val invalidLines = Table("lines", emptyList, List("5"), List("5", "1"), List("5", "1", "G", "1"))

    forAll(invalidLines) { (lines: List[String]) =>
      // Act
      val result = convert(lines)

      // Assert
      result.left.value should equal("Can't convert lawn due to invalid content's size: %d".format(lines.size))
    }
  }

  it should "return an error message if the top right corner line is invalid" taggedAs UnitTest in {
    val invalidLines = Table("topRightCornerLine", "AAAAA", "A 1", "1 A", "1 2 6")

    forAll(invalidLines) { (topRightCornerLine: String) =>
      // Arrange
      val input = List(topRightCornerLine, "1 2 N", "GAGA")

      // Act
      val result = convert(input)

      // Assert
      result.left.value should equal("Can't convert lawn due to invalid content: %s".format(topRightCornerLine))
    }
  }

  it should "return an error message if a mower line is invalid" taggedAs IntegrationTest in {
    val invalidLines = Table("mowersLines", List("1", "G"), List("1 2 N", "Q"), List("1 2 N", "G", "2", "G"))

    forAll(invalidLines) { (mowersLines: List[String]) =>
      // Arrange
      val input = "5 5" :: mowersLines

      // Act
      val result = convert(input)

      // Assert
      result.left.value should startWith("Can't convert lawn due to invalid mower content")
    }
  }

  it should "return a ParsedLawn instance if incoming content is valid" taggedAs IntegrationTest in {
    // Arrange
    val input = List("5 5", "1 2 N", "GA")

    // Act
    val result = convert(input)

    // Assert
    val parsedLawn = result.right.value
    parsedLawn.topRightCorner should equal((5, 5))
    parsedLawn.mowers.size should equal(1)

    val parsedMower = parsedLawn.mowers.head
    parsedMower.initialPosition should equal((1, 2, 'N'))
    parsedMower.actions should equal(List('G', 'A'))
  }

}
