package fr.mistertie.lawnmower.parsing.common.reader.file.flat

import java.io.File

import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.UnitTest

/**
 * Test cases about [[FlatFileReader]].
 * @see [[BaseSpec]].
 */
@UnitTest
class FlatFileReaderSpec extends BaseSpec {

  "A FlatFileReader" should "return an error message when source file is not found" in {
    // Arrange
    val missingFile = new File("missingFile")

    // Act
    val result = FlatFileReader(missingFile)

    // Assert
    result.reading().left.value should startWith("java.io.FileNotFoundException: missingFile")
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
