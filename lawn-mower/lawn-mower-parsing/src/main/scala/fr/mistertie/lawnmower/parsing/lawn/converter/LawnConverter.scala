package fr.mistertie.lawnmower.parsing.lawn.converter

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.parsing.lawn.model.ParsedLawn
import fr.mistertie.lawnmower.parsing.mower.converter.MowerConverter
import fr.mistertie.lawnmower.parsing.mower.model.ParsedMower

/**
 * @inheritdoc
 * Converter from a [[List]] of lines to a [[ParsedLawn]]. The list should contain an odd number of lines (at least
 * three).
 */
object LawnConverter extends Converter[List[String], ParsedLawn] {

  val topRightCornerLinePattern = "([0-9]) ([0-9])".r

  /**
   * @param input the content to be converted.
   * @return the conversion result.
   */
  // TODO: Comment this method
  def convert(input: List[String]): Either[String, ParsedLawn] = {
    val linesCount = input.size
    if (linesCount >= 3 && linesCount % 2 != 0) {
      val topRightCornerLine: String = input.head
      try {
        val topRightCornerLinePattern(abscissa, ordinate) = topRightCornerLine
        var parsedMowers: List[ParsedMower] = List.empty

        for (mower <- input.tail.grouped(2)) {
          MowerConverter.convert(mower.head, mower.last) match {
            case Right(parsedMower) => parsedMowers = parsedMowers :+ parsedMower
            case Left(error) => return Left(("Can't convert lawn due to invalid mower content: %s," +
              "%s").format(mower.head, mower.last))
          }
        }

        Right(ParsedLawn((abscissa.toInt, ordinate.toInt), parsedMowers))

      } catch {
        case _: MatchError => Left("Can't convert lawn due to invalid content: %s".format(topRightCornerLine))
      }
    } else Left("Can't convert lawn due to invalid content's size: %d".format(linesCount))
  }
}
