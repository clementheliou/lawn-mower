package fr.mistertie.lawnmower.parsing.mower.model

/**
 * A mower as a result of source parsing.
 * @param initialPosition the parsed mower's initial position.
 * @param actions the parsed mower's actions.
 */
class ParsedMower private(val initialPosition: (Int, Int, Char), val actions: List[Char]) {}

/**
 * Companion object of the [[ParsedMower]] class.
 */
object ParsedMower {

  /**
   * Well-known apply method allowing an easy use of the primary constructor.
   * @param initialPosition the mower's initial position.
   * @param actions the mower's actions.
   * @return the created instance.
   */
  def apply(initialPosition: (Int, Int, Char), actions: List[Char]) = new ParsedMower(initialPosition, actions)
}
