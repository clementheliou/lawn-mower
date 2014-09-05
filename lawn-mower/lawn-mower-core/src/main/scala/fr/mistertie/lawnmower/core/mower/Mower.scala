package fr.mistertie.lawnmower.core.mower

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint.{CardinalPoint, rotateLeft, rotateRight}
import fr.mistertie.lawnmower.core.lawn.Lawn
import fr.mistertie.lawnmower.core.point.Point

/**
 * A mower, represented as a pair of position and orientation inside a parent lawn.
 * @param position the mower's position.
 * @param orientation the mower's orientation.
 * @param parent the lawn to be mown.
 */
class Mower private(var position: Point, var orientation: CardinalPoint, val parent: Lawn) {

  parent.initializePosition(position)

  /**
   * Explore the mower's parent lawn from the given actions then return the current position and orientation.
   * @param actions an ordered List of actions to be executed.
   * @return a tuple (abscissa, ordinate, orientation).
   */
  def exploreLawn(actions: List[Char]) = {
    actions.foreach { case 'A' => position = parent.checkAndBookNextPosition(position, orientation)
    case 'D' => orientation = rotateRight(orientation)
    case 'G' => orientation = rotateLeft(orientation)
    case _ => // Skip the other ones!
    }

    (position.abscissa, position.ordinate, orientation)
  }

  /**
   * Display the mower's position and orientation.
   * @return the string to be displayed.
   */
  override def toString: String = "(%d,%d,%s)".format(position.abscissa, position.ordinate, orientation)
}

/**
 * Companion object of the [[Mower]] class.
 */
object Mower {

  /**
   * Well-known apply method allowing an easy use of the primary constructor.
   * @param position the mower's position.
   * @param orientation the mower's orientation.
   * @param parent the lawn to be mown.
   * @return the created instance.
   */
  def apply(position: Point, orientation: CardinalPoint, parent: Lawn) = new Mower(position, orientation, parent)
}
