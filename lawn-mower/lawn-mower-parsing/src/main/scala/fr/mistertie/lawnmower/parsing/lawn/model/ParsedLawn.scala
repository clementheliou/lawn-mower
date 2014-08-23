package fr.mistertie.lawnmower.parsing.lawn.model

import fr.mistertie.lawnmower.parsing.mower.model.ParsedMower

/**
 * A lawn as a result of source parsing.
 * @param topRightCorner the parsed lawn's top right corner's coordinates.
 * @param mowers the mowers related to the parsed lawn.
 */
class ParsedLawn private(val topRightCorner: (Int, Int), val mowers: List[ParsedMower]) {}

/**
 * Companion object of the [[ParsedLawn]] class.
 */
object ParsedLawn {

  /**
   * Well-known apply method allowing an easy use of the primary constructor.
   * @param topRightCorner the parsed lawn's top right corner's coordinates.
   * @param mowers the mowers related to the parsed lawn.
   * @return the created instance.
   */
  def apply(topRightCorner: (Int, Int), mowers: List[ParsedMower]) = new ParsedLawn(topRightCorner, mowers)
}
