package fr.mistertie.lawnmower.parsing.lawn.converter

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.parsing.lawn.model.ParsedLawn
import fr.mistertie.lawnmower.parsing.mower.converter.MowerConverter
import fr.mistertie.lawnmower.parsing.mower.converter.MowerConverter.{convert => convertMower}
import fr.mistertie.lawnmower.parsing.mower.model.ParsedMower

import scala.collection.immutable.List.empty

/**
 * @inheritdoc
 * Converter from a [[List]] of lines to a [[ParsedLawn]]. The list should contain an odd number of lines (at least
 * three).
 */
object LawnConverter extends Converter[List[String], ParsedLawn] {

  val topRightCornerLinePattern = """\s*([0-9])\s+([0-9])\s*""".r

  /**
   * Convert the given list of lines to a [[ParsedLawn]] checking content size, using regular expression to check
   * format and delegating mowers parsing to a [[MowerConverter]].
   * @param input the content to be converted.
   * @return the conversion result.
   */
  def convert(input: List[String]) = {
    val linesCount = input.size

    if (isValidLinesCount(linesCount)) convertLawn(input.head, input.tail)
    else Left("Can't convert lawn due to invalid content's size: %d".format(linesCount))
  }

  /**
   * Convert the given lines to a [[ParsedLawn]] using regular expression to check format and delegating mowers
   * parsing to a [[MowerConverter]].
   * @param topRightCornerLine the line related to the lawn's top right corner.
   * @param mowersLines the line related to the lawn's mowers.
   * @return the conversion result.
   */
  private def convertLawn(topRightCornerLine: String, mowersLines: List[String]) = {
    try {
      val topRightCornerLinePattern(abscissa, ordinate) = topRightCornerLine

      convertMowers(mowersLines) match {
        case Left(error) => Left(error)
        case Right(parsedMowers) => Right(ParsedLawn((abscissa.toInt, ordinate.toInt), parsedMowers))
      }

    } catch {
      case _: MatchError => Left("Can't convert lawn due to invalid content: %s".format(topRightCornerLine))
    }
  }

  /**
   * Convert the given lines to a [[List]] of [[ParsedMower]] delegating the conversion to a [[MowerConverter]].
   * @param mowersLines the source lines.
   * @return the conversion result.
   */
  private def convertMowers(mowersLines: List[String]): Either[String, List[ParsedMower]] = {
    Right(mowersLines.grouped(2).map(l => (l.head, l.last)).foldLeft(empty[ParsedMower]) { (parsedMowers, input) =>
      convertMower(input) match {
        case Right(parsedMower) => parsedMowers :+ parsedMower
        case Left(_) => return Left("Can't convert lawn due to invalid mower content: %s".format(input))
      }
    })
  }

  /**
   * Check if the given lines count is valid. That is to say is an odd greater or equals to three.
   * @param count the value to be checked.
   * @return the checking result.
   */
  private def isValidLinesCount(count: Int) = count >= 3 && isOdd(count)

  /**
   * Check if the given number is an odd.
   * @param i the number to be checked.
   * @return the checking result.
   */
  private def isOdd(i: Int) = i % 2 != 0

}
