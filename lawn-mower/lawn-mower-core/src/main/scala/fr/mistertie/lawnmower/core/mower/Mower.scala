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

  parent.initPosition(position)

  /**
   * Given the mower orientation, rotate it by 90 degree to the left.
   */
  def rotatePositionLeft() {
    orientation = rotateLeft(orientation)
  }

  /**
   * Given the mower orientation, rotate it by 90 degree to the right.
   */
  def rotatePositionRight() {
    orientation = rotateRight(orientation)
  }
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
