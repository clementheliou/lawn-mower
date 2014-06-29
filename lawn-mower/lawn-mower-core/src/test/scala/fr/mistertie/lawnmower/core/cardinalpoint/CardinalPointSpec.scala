package fr.mistertie.lawnmower.core.cardinalpoint

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint._
import fr.mistertie.lawnmower.core.test.UnitSpec

/**
 * Test cases about [[CardinalPoint]].
 * @see [[UnitSpec]]
 */
class CardinalPointSpec extends UnitSpec {

  "The North cardinal point" should "return the East one when rotating to the right" in {
    rotateRight(NORTH) should equal(EAST)
  }

  it should "return the West one when rotating to the left" in {
    rotateLeft(NORTH) should equal(WEST)
  }

  "The South cardinal point" should "return the West one when rotating to the right" in {
    rotateRight(SOUTH) should equal(WEST)
  }

  it should "return the East one when rotating to the left" in {
    rotateLeft(SOUTH) should equal(EAST)
  }

  "The East cardinal point" should "return the South one when rotating to the right" in {
    rotateRight(EAST) should equal(SOUTH)
  }

  it should "return the North one when rotating to the left" in {
    rotateLeft(EAST) should equal(NORTH)
  }

  "The West cardinal point" should "return the North one when rotating to the right" in {
    rotateRight(WEST) should equal(NORTH)
  }

  it should "return the South one when rotating to the left" in {
    rotateLeft(WEST) should equal(SOUTH)
  }
}
