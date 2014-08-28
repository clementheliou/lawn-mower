package fr.mistertie.lawnmower.core.lawn

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.point.Point

import scala.collection.immutable.HashSet

/**
 * A lawn, represented as a grid of [[Point]]s from the bottom left corner (always (0,0)) to the top right one,
 * provided during the instantiation.
 * @param topRightAbscissa the top right point's abscissa.
 * @param topRightOrdinate the top right point's ordinate.
 */
class Lawn private(topRightAbscissa: Int, topRightOrdinate: Int) {

  if (topRightAbscissa == 0 && topRightOrdinate == 0) {
    throw new IllegalArgumentException("A lawn should have at least one point.")
  }

  if (topRightAbscissa < 0 || topRightOrdinate < 0) {
    throw new IllegalArgumentException("A lawn can't have top right corner with negative coordinate.")
  }

  val bottomLeftCorner = Point(0, 0)
  val topRightCorner = Point(topRightAbscissa, topRightOrdinate)
  private var bookedPositions = new HashSet[Point]

  /**
   * Initialize the given position if free and inside the lawn's bounds. Initialize means "booking the position without
   * releasing an existing one".
   * @param position the position to be initialized.
   * @throws IllegalArgumentException if the position is already booked or out of bounds.
   */
  def initializePosition(position: Point) {
    if (isBookedAt(position)) {
      throw new IllegalArgumentException("Position already booked: " + position)
    }

    if (isOutOfBounds(position)) {
      throw new IllegalArgumentException("Position out of bounds: " + position)
    }

    bookedPositions += position
  }

  /**
   * Book the next position from the given one, releasing it. If the next position is
   * out of bounds or booked, do nothing and return the given position.
   * @param currentPosition the source position.
   * @param orientation the source orientation.
   * @return the next available position if this one is valid, the current one otherwise.
   */
  def checkAndBookNextPosition(currentPosition: Point, orientation: CardinalPoint): Point = orientation match {
    case EAST => checkAndBookPosition(currentPosition, currentPosition.translate(Point(1, 0)))
    case NORTH => checkAndBookPosition(currentPosition, currentPosition.translate(Point(0, 1)))
    case SOUTH => checkAndBookPosition(currentPosition, currentPosition.translate(Point(0, -1)))
    case WEST => checkAndBookPosition(currentPosition, currentPosition.translate(Point(-1, 0)))
  }

  /**
   * Book the next position, releasing the current one. If next position is out of bounds or
   * booked, do nothing and return the current position.
   * @param currentPosition the source and currently booked position.
   * @param nextPosition the position to be checked and booked
   * @return the next position if this one is valid, the current one otherwise.
   */
  private def checkAndBookPosition(currentPosition: Point, nextPosition: Point) = {
    if (isBookedAt(nextPosition) || isOutOfBounds(nextPosition)) currentPosition
    else bookPosition(currentPosition, nextPosition)
  }

  /**
   * Check if the given position is out of the lawn's bounds.
   * @param position the position to be checked.
   * @return the checking result.
   */
  private def isOutOfBounds(position: Point) = {
    val abscissas = bottomLeftCorner.abscissa.to(topRightCorner.abscissa)
    val ordinates = bottomLeftCorner.ordinate.to(topRightCorner.ordinate)
    !abscissas.contains(position.abscissa) || !ordinates.contains(position.ordinate)
  }

  /**
   * Book the given position releasing the current one.
   * @param currentPosition the current position to release.
   * @param positionToBook the position to book.
   * @return the booked position.
   */
  private def bookPosition(currentPosition: Point, positionToBook: Point) = {
    bookedPositions = bookedPositions + positionToBook - currentPosition
    positionToBook
  }

  /**
   * Check if the given position is booked on this lawn.
   * @param position the position to be checked.
   * @return the checking result.
   */
  def isBookedAt(position: Point) = bookedPositions.contains(position)

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
  def apply(topRightAbscissa: Int, topRightOrdinate: Int) = new Lawn(topRightAbscissa, topRightOrdinate)
}