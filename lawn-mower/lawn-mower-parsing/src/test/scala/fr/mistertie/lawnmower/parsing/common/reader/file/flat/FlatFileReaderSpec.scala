package fr.mistertie.lawnmower.parsing.common.reader.file.flat

import java.io.File

import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.UnitTest
import org.scalatest.EitherValues

/**
 * Test cases about [[FlatFileReader]].
 */
@UnitTest
class FlatFileReaderSpec extends BaseSpec with EitherValues {

  "A FlatFileReader" should "return an error message when source file is not found" in {
    // Arrange
    val missingFile = new File("missingFile")

    // Act
    val result = FlatFileReader(missingFile)

    // Assert
    result.reading().left.value should equal("missingFile (No such file or directory)")
  }

  it should "return content as lines when source file is found" in {
    // Arrange
    val existingFile = new File(getClass.getResource("/existingFile").getPath)

    // Act
    val result = FlatFileReader(existingFile)

    // Assert
    result.reading().right.value should equal(List("firstLine", "secondLine"))
  }
}