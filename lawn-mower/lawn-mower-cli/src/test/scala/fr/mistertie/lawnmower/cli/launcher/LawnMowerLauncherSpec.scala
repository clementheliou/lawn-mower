package fr.mistertie.lawnmower.cli.launcher

import java.io.File

import fr.mistertie.lawnmower.test.spec.BaseSpec
import fr.mistertie.lawnmower.test.tag.IntegrationTest

/**
 * Test cases about [[LawnMowerLauncher]].
 */
@IntegrationTest
class LawnMowerLauncherSpec extends BaseSpec {

  "A lawn mower launcher" should "return exception when reading failed" in {
    // Arrange
    val missingFile = new File("missingFile")

    // Act
    val result = LawnMowerLauncher.launch(missingFile)

    // Assert
    result.isFailure should equal(true)
    result.failed.get should have message "missingFile (No such file or directory)"
  }

  it should "return an exception when read file has an invalid size" in {
    val invalidFileNames = Table("fileName", "emptyFile", "fileWithAnEvenLinesNumber", "tooShortFile")

    forAll(invalidFileNames) { (fileName) =>
      // Arrange
      val invalidFilePath = getClass.getResource("/file/invalid/common/conversion/%s".format(fileName)).getPath
      val invalidFile = new File(invalidFilePath)

      // Act
      val result = LawnMowerLauncher.launch(invalidFile)

      // Assert
      result.isFailure should equal(true)
      result.failed.get.getMessage should startWith("Can't convert lawn due to invalid content's size:")
    }
  }

  it should "return an exception when read file contains an invalid lawn line format" in {
    // Arrange
    val invalidFilePath = getClass.getResource("/file/invalid/lawn/conversion/invalidLawnLine").getPath
    val invalidFile = new File(invalidFilePath)

    // Act
    val result = LawnMowerLauncher.launch(invalidFile)

    // Assert
    result.isFailure should equal(true)
    result.failed.get should have message "Can't convert lawn due to invalid content: %s".format("You loose")
  }

  it should "return an exception when read file contains an invalid mower line format" in {
    val invalidFileNames = Table("fileName", "invalidFirstMowerActionsLine", "invalidFirstMowerPositionLine",
      "invalidMowerActionsLineAfterValidMower", "invalidMowerPositionLineAfterValidMower")

    forAll(invalidFileNames) { (fileName) =>
      // Arrange
      val invalidFilePath = getClass.getResource("/file/invalid/mower/conversion/%s".format(fileName)).getPath
      val invalidFile = new File(invalidFilePath)

      // Act
      val result = LawnMowerLauncher.launch(invalidFile)

      // Assert
      result.isFailure should equal(true)
      result.failed.get.getMessage should startWith("Can't convert lawn due to invalid mower content:")
    }
  }

  it should "return an exception when read file contains a lawn without any point" in {
    // Arrange
    val invalidFilePath = getClass.getResource("/file/invalid/lawn/creation/lawnWithoutAnyPoint").getPath
    val invalidFile = new File(invalidFilePath)

    // Act
    val result = LawnMowerLauncher.launch(invalidFile)

    // Assert
    result.isFailure should equal(true)
    result.failed.get should have message "A lawn should have at least one point."
  }

  it should "return an exception when read file contains a mower out of its lawn bounds" in {
    val invalidFileNames = Table("fileName", "mowerOutOfEastBound", "mowerOutOfNorthBound")

    forAll(invalidFileNames) { (fileName) =>
      // Arrange
      val invalidFilePath = getClass.getResource("/file/invalid/mower/creation/%s".format(fileName)).getPath
      val invalidFile = new File(invalidFilePath)

      // Act
      val result = LawnMowerLauncher.launch(invalidFile)

      // Assert
      result.isFailure should equal(true)
      result.failed.get.getMessage should startWith("Position out of bounds:")
    }
  }

  it should "return an exception when read file contains a mower creation on booked position" in {
    // Arrange
    val invalidFilePath = getClass.getResource("/file/invalid/mower/creation/mowerCreationOnBookedPosition").getPath
    val invalidFile = new File(invalidFilePath)

    // Act
    val result = LawnMowerLauncher.launch(invalidFile)

    // Assert
    result.isFailure should equal(true)
    result.failed.get.getMessage should startWith("Position already booked: Point(1,2)")
  }

  it should "success when read file content is valid" in {
    val validFileNames = Table("fileName", "multipleMowersWithCrash", "multipleMowersWithoutCrash", "singleMower",
      "singleMowerAtLawnBounds")

    forAll(validFileNames) { (fileName) =>
      // Arrange
      val validFilePath = getClass.getResource("/file/valid/%s".format(fileName)).getPath
      val validFile = new File(validFilePath)

      // Act
      val result = LawnMowerLauncher.launch(validFile)

      // Assert
      result.isSuccess should equal(true)
    }
  }
}
