package fr.mistertie.lawnmower.parsing.mower.converter

import fr.mistertie.lawnmower.parsing.mower.converter.MowerConverter.convert
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.UnitTest
import org.scalatest.EitherValues

/**
 * Test cases about [[MowerConverter]].
 */
@UnitTest
class MowerConverterSpec extends BaseSpec with EitherValues {

  "A mower converter" should "return the incoming error message if found" in {
    // Arrange
    val incomingMessage = "Generated for test purpose"
    val readContent = Left(incomingMessage)

    // Act
    val result = convert(readContent)

    // Assert
    result.left.value should equal(incomingMessage)
  }

  it should "return an error message if incoming position line format is invalid" in {
    val invalidFormats = Table("positionLine", "AAAAA", "A A 2", "1", "1 2", "1 2 2", "1 2 Q")

    forAll(invalidFormats) { (positionLine: String) =>
      // Arrange
      val readContent = Right(positionLine, "GAGA")

      // Act
      val result = convert(readContent)

      // Assert
      result.left.value should equal("Can't convert mower due to invalid content: (%s,GAGA)".format(positionLine))
    }
  }

  it should "return an error message if incoming actions line format is invalid" in {
    val invalidFormats = Table("actionsLine", "1 2 N", "QQQQ", "ADQHFLKF", "G A G")

    forAll(invalidFormats) { (actionsLine: String) =>
      // Arrange
      val readContent = Right("1 2 N", actionsLine)

      // Act
      val result = convert(readContent)

      // Assert
      result.left.value should equal("Can't convert mower due to invalid content: (1 2 N,%s)".format(actionsLine))
    }
  }

  it should "return a ParsedMower instance if incoming content is valid" in {
    // Arrange
    val readContent = Right("1 2 N", "GAGA")

    // Act
    val result = convert(readContent)

    // Assert
    val parsedMower = result.right.value
    parsedMower.initialPosition should equal((1, 2, 'N'))
    parsedMower.actions should equal(List('G', 'A', 'G', 'A'))
  }
}
