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

  private var bookedPositions = new HashSet[Point]
  val bottomLeftCorner = Point(0, 0)
  val topRightCorner = Point(topRightAbscissa, topRightOrdinate)

  /**
   * Initialise the given position if free. That is to say booking the position without releasing an existing one.
   * @param position the position to be initialised.
   * @throws IllegalArgumentException if the position is already booked.
   */
  def initPosition(position: Point) {
    if (isBookedAt(position)) {
      throw new IllegalArgumentException("Position already booked: " + position)
    }
    bookedPositions += position
  }

  /**
   * Book the next position from the given one, releasing it. If the next position is
   * out of bound or taken, do nothing and return the given position.
   * @param currentPosition the source position.
   * @param orientation the source orientation.
   * @return the next available position if this one is valid, the current one otherwise.
   */
  def checkAndBookNextPosition(currentPosition: Point, orientation: CardinalPoint): Point = orientation match {
    case EAST => checkAndBookNextEastPosition(currentPosition)
    case NORTH => checkAndBookNextNorthPosition(currentPosition)
    case SOUTH => checkAndBookNextSouthPosition(currentPosition)
    case WEST => checkAndBookNextWestPosition(currentPosition)
  }

  /**
   * Book the next east position from the given one, releasing it. If the next position is out of east bound or
   * taken, do nothing and return the given position.
   * @param currentPosition the source position.
   * @return the next available east position if this one is valid, the current one otherwise.
   */
  private def checkAndBookNextEastPosition(currentPosition: Point) = {
    if (isBookedAt(currentPosition) || isEastBoundAt(currentPosition)) currentPosition
    else bookPosition(currentPosition, currentPosition.translate(Point(1, 0)))
  }

  /**
   * Book the next north position from the given one, releasing it. If the next position is out of north bound or
   * taken, do nothing and return the given position.
   * @param currentPosition the source position.
   * @return the next available north position if this one is valid, the current one otherwise.
   */
  private def checkAndBookNextNorthPosition(currentPosition: Point) = {
    if (isBookedAt(currentPosition) || isNorthBoundAt(currentPosition)) currentPosition
    else bookPosition(currentPosition, currentPosition.translate(Point(0, 1)))
  }

  /**
   * Book the next south position from the given one, releasing it. If the next position is out of south bound or
   * taken, do nothing and return the given position.
   * @param currentPosition the source position.
   * @return the next available south position if this one is valid, the current one otherwise.
   */
  private def checkAndBookNextSouthPosition(currentPosition: Point) = {
    if (isBookedAt(currentPosition) || isSouthBoundAt(currentPosition)) currentPosition
    else bookPosition(currentPosition, currentPosition.translate(Point(0, -1)))
  }

  /**
   * Book the next west position from the given one, releasing it. If the next position is out of west bound or
   * taken, do nothing and return the given position.
   * @param currentPosition the source position.
   * @return the next available west position if this one is valid, the current one otherwise.
   */
  private def checkAndBookNextWestPosition(currentPosition: Point) = {
    if (isBookedAt(currentPosition) || isWestBoundAt(currentPosition)) currentPosition
    else bookPosition(currentPosition, currentPosition.translate(Point(-1, 0)))
  }

  /**
   * Book the given position releasing the current one.
   * @param currentPosition the current position to release.
   * @param positionToBook the position to book.
   * @return the booked position.
   */
  private def bookPosition(currentPosition: Point, positionToBook: Point) = {
    bookedPositions += positionToBook
    bookedPositions -= currentPosition
    positionToBook
  }

  /**
   * Check if the given position is at the lawn's east bound.
   * @param position the position to check.
   * @return the checking result.
   */
  private def isEastBoundAt(position: Point) = position.abscissa == topRightCorner.abscissa

  /**
   * Check if the given position is at the lawn's north bound.
   * @param position the position to check.
   * @return the checking result.
   */
  private def isNorthBoundAt(position: Point) = position.ordinate == topRightCorner.ordinate

  /**
   * Check if the given position is at the lawn's south bound.
   * @param position the position to check.
   * @return the checking result.
   */
  private def isSouthBoundAt(position: Point) = position.ordinate == bottomLeftCorner.ordinate

  /**
   * Check if the given position is at the lawn's west bound.
   * @param position the position to check.
   * @return the checking result.
   */
  private def isWestBoundAt(position: Point) = position.abscissa == bottomLeftCorner.abscissa

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
  def apply(topRightAbscissa: Short, topRightOrdinate: Short) = new Lawn(topRightAbscissa, topRightOrdinate)
}