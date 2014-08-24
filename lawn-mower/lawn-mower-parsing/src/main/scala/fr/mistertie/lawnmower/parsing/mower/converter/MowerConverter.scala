package fr.mistertie.lawnmower.parsing.mower.converter

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.parsing.mower.model.ParsedMower

/**
 * @inheritdoc
 * Converter from a pair of Strings to a [[ParsedMower]]. The pair should contains a line for the initial position 
 * and another one for the mower's actions.
 */
object MowerConverter extends Converter[(String, String), ParsedMower] {

  val actionsLinePattern = "([ADG]+)".r
  val positionLinePattern = "([0-9]) ([0-9]) ([NEWS])".r

  /**
   * Convert the given pair of lines to a [[ParsedMower]] mainly using regular expressions to check expected formats.
   * If the input [[Either]] is a [[Left]] containing an error message, return it.
   * @param input the content to be converted.
   * @return the conversion result.
   */
  def convert(input: Either[String, (String, String)]) = {
    if (input.isRight) {
      val (positionLine, actionsLine) = input.right.get

      try {
        val actionsLinePattern(actions) = actionsLine
        val positionLinePattern(abscissa, ordinate, orientation) = positionLine
        Right(ParsedMower((abscissa.toInt, ordinate.toInt, orientation.charAt(0)), actions.toList))
      } catch {
        case _: MatchError => Left("Can't convert mower due to invalid content: %s".format((positionLine, actionsLine)))
      }

    } else Left(input.left.get)
  }
}