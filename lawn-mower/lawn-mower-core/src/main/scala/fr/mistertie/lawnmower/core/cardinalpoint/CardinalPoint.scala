package fr.mistertie.lawnmower.core.cardinalpoint

/**
 * Enumeration of the well-known cardinal points.
 * @see [[Enumeration]]
 */
object CardinalPoint extends Enumeration {

  /**
   * Alias used to improve this enumeration's type readability.
   */
  type CardinalPoint = Value

  val NORTH = Value('N')
  val EAST = Value('E')
  val SOUTH = Value('S')
  val WEST = Value('W')

  /**
   * Given the source cardinal point, rotate it by 90 degree to left.
   * @param source the cardinal point to be rotated.
   * @return the rotation result.
   */
  def rotateLeft(source: CardinalPoint) = source match {
    case NORTH => WEST
    case EAST => NORTH
    case SOUTH => EAST
    case WEST => SOUTH
  }

  /**
   * Given the source cardinal point, rotate it by 90 degree to right.
   * @param source the cardinal point to be rotated.
   * @return the rotation result.
   */
  def rotateRight(source: CardinalPoint) = source match {
    case NORTH => EAST
    case EAST => SOUTH
    case SOUTH => WEST
    case WEST => NORTH
  }
}
