package fr.mistertie.lawnmower.core.point

/**
 * A 2D point, represented by an abscissa/ordinate pair.
 * @param abscissa the point's abscissa.
 * @param ordinate the point's ordinate.
 */
case class Point(abscissa: Int, ordinate: Int) {

  /**
   * Apply the given translation and return the result.
   * @param translation the translation to apply as a point.
   * @return the translation application's result.
   */
  def translate(translation: Point) = Point(abscissa + translation.abscissa, ordinate + translation.ordinate)
}
