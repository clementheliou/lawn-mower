package fr.mistertie.lawnmower.core.lawn

import fr.mistertie.lawnmower.core.test.UnitSpec

/**
 * Test cases about [[Lawn]].
 * @see [[UnitSpec]].
 */
class LawnSpec extends UnitSpec {

  "A lawn without point" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(0, 0)
    } should have message "A lawn should have at least one point."
  }

  "A lawn with negative top right corner's abscissa" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(-4, 12)
    } should have message "A lawn can't have top right corner with negative coordinate."
  }

  "A lawn with negative top right corner's ordinate" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(12, -4)
    } should have message "A lawn can't have top right corner with negative coordinate."
  }

  "A lawn with negatives top right corner's coordinates" should "produce an IllegalArgumentException" in {
    the[IllegalArgumentException] thrownBy {
      Lawn(-4, -4)
    } should have message "A lawn can't have top right corner with negative coordinate."
  }
}
