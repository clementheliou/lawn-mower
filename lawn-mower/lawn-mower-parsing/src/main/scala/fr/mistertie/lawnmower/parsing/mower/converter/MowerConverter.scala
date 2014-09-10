package fr.mistertie.lawnmower.parsing.mower.converter

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.parsing.mower.model.ParsedMower

/**
 * @inheritdoc
 * Converter from a pair of Strings to a [[ParsedMower]]. The pair should contains a line for the initial position 
 * and another one for the mower's actions.
 */
object MowerConverter extends Converter[(String, String), ParsedMower] {

  val actionsLinePattern = """\s*([ADG]+)\s*""".r
  val positionLinePattern = """\s*([0-9]+)\s+([0-9]+)\s+([NEWS])\s*""".r

  /**
   * Convert the given pair of lines to a [[ParsedMower]] mainly using regular expressions to check expected formats.
   * @param input the content to be converted.
   * @return the conversion result.
   */
  def convert(input: (String, String)) = {
    val (positionLine, actionsLine) = input

    try {
      val actionsLinePattern(actions) = actionsLine
      val positionLinePattern(abscissa, ordinate, orientation) = positionLine
      Right(ParsedMower((abscissa.toInt, ordinate.toInt, orientation.charAt(0)), actions.toList))
    } catch {
      case _: MatchError => Left("Can't convert mower due to invalid content: %s".format((positionLine, actionsLine)))
    }
  }
}