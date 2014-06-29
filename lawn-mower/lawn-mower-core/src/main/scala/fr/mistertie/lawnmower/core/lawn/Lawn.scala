package fr.mistertie.lawnmower.core.lawn

import fr.mistertie.lawnmower.core.point.Point

/**
 * A lawn, represented as a grid of [[Point]]s from the bottom left corner (always (0,0)) to the top right one,
 * provided during the instantiation.
 * @param topRightAbscissa the top right point's abscissa.
 * @param topRightOrdinate the top right point's ordinate.
 */
class Lawn private(topRightAbscissa: Short, topRightOrdinate: Short) {

  if (topRightAbscissa == 0 && topRightOrdinate == 0) {
    throw new IllegalArgumentException("A lawn should have at least one point.")
  }

  if (topRightAbscissa < 0 || topRightOrdinate < 0) {
    throw new IllegalArgumentException("A lawn can't have top right corner with negative coordinate.")
  }

  val bottomLeftCorner = Point(0, 0)
  val topRightCorner = Point(topRightAbscissa, topRightOrdinate)
}

/**
 * Companion object of the [[Lawn]] class.
 */
object Lawn {

  /**
   * Well-known apply method allowing an easy use of the primary constructor.
   * @param topRightAbscissa the top right point's abscissa.
   * @param topRightOrdinate the top right point's ordinate.
   * @return the created instance.
   */
  def apply(topRightAbscissa: Short, topRightOrdinate: Short) = new Lawn(topRightAbscissa, topRightOrdinate)
}