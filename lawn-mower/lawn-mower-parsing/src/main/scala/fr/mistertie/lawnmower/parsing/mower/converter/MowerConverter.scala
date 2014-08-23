package fr.mistertie.lawnmower.parsing.mower.converter

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.parsing.mower.model.ParsedMower

object MowerConverter extends Converter[(String, String), ParsedMower] {

  val actionsPattern = "([ADG]+)".r
  val positionPattern = "([0-9]) ([0-9]) ([NEWS])".r

  def convert(readContent: Either[String, (String, String)]): Either[String, ParsedMower] = {
    if (readContent.isLeft) Left(readContent.left.get)
    else {
      val (positionLine, actionsLine) = readContent.right.get
      try {
        val positionPattern(abscissa, ordinate, orientation) = positionLine
        val actionsPattern(actions) = actionsLine
        Right(ParsedMower((abscissa.toInt, ordinate.toInt, orientation.charAt(0)), actions.toList))
      } catch {
        case e: MatchError => Left("Can't convert mower due to invalid content: %s".format((positionLine, actionsLine)))
      }
    }
  }
}