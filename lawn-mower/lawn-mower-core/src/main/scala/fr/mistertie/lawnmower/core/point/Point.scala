package fr.mistertie.lawnmower.core.point

/**
 * A 2D point, represented by an abscissa/ordinate pair.
 * @param abscissa the point's abscissa.
 * @param ordinate the point's ordinate.
 */
class Point private(abscissa: Short, ordinate: Short) {
  val coordinates = (abscissa, ordinate)
}

/**
 * Companion object of the [[Point]] class.
 */
object Point {

  /**
   * Well-known apply method allowing an easy use of the primary constructor.
   * @param abscissa the point's abscissa.
   * @param ordinate the point's ordinate.
   * @return the created instance.
   */
  def apply(abscissa: Short, ordinate: Short) = new Point(abscissa, ordinate)
}