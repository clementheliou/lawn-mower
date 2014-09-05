package fr.mistertie.lawnmower.parsing.mower.converter

import fr.mistertie.lawnmower.parsing.mower.converter.MowerConverter.convert
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.UnitTest

/**
 * Test cases about [[MowerConverter]].
 */
@UnitTest
class MowerConverterSpec extends BaseSpec {

  it should "return an error message if incoming position line is invalid" in {
    val invalidFormats = Table("positionLine", "AAAAA", "A A 2", "1", "1 2", "1 2 2", "1 2 Q")

    forAll(invalidFormats) { (positionLine: String) =>
      // Arrange
      val input = (positionLine, "GAGA")

      // Act
      val result = convert(input)

      // Assert
      result.left.value should equal("Can't convert mower due to invalid content: (%s,GAGA)".format(positionLine))
    }
  }

  it should "return an error message if incoming actions line is invalid" in {
    val invalidFormats = Table("actionsLine", "1 2 N", "QQQQ", "ADQHFLKF", "G A G")

    forAll(invalidFormats) { (actionsLine: String) =>
      // Arrange
      val input = ("1 2 N", actionsLine)

      // Act
      val result = convert(input)

      // Assert
      result.left.value should equal("Can't convert mower due to invalid content: (1 2 N,%s)".format(actionsLine))
    }
  }

  it should "return a ParsedMower instance if incoming content is valid" in {
    val validContent = Table("content", ("1 2 N", "GA"), (" 1  2  N ", " GA "))

    forAll(validContent) { (content) =>
      // Act
      val result = convert(content)

      // Assert
      val parsedMower = result.right.value
      parsedMower.initialPosition should equal((1, 2, 'N'))
      parsedMower.actions should equal(List('G', 'A'))
    }
  }
}
