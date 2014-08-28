package fr.mistertie.lawnmower.parsing.common.reader

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.UnitTest
import org.scalatest.EitherValues

/**
 * Test cases about [[Reader]] class.
 */
@UnitTest
class ReaderSpec extends BaseSpec with EitherValues {

  /**
   * Dummy implicit converter from String to String. This implementation always returns the input value and allows the
   * [[Reader]] isolated testing.
   */
  implicit val stringToStringDummyConverter = new Converter[String, String] {
    def convert(input: String): Either[String, String] = Right(input)
  }

  "A reader" should "not trigger conversion if an error occurred during reading" in {
    // Arrange
    val readingFailureMessage = "Error during reading"
    val failedReading = { () => Left(readingFailureMessage)}

    // Act
    val result = StringReader(failedReading).as[String]

    // Assert
    result.left.value should equal(readingFailureMessage)
  }

  it should "trigger conversion after successful reading" in {
    // Arrange
    val readingResult = "1 2 N"
    val successfulReading = { () => Right(readingResult)}

    // Act
    val result = StringReader(successfulReading).as[String]

    // Assert
    result.right.value should equal(readingResult)
  }

}

/**
 * Dummy implementation of the [[Reader]] class allowing its isolated testing. This one read a single String.
 */
object StringReader {

  /**
   * Well-known apply method allowing an easy use of the [[Reader]]'s primary constructor.
   * @param reading the reading process to be applied.
   * @return a reader of single string.
   */
  def apply(reading: () => Either[String, String]) = new Reader[String](reading)
}
