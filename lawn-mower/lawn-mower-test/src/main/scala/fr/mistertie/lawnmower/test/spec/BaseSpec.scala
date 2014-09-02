package fr.mistertie.lawnmower.test.spec

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{EitherValues, FlatSpec, Matchers, OptionValues}

/**
 * Base spec for both integration and unit tests using ScalaTest.
 * @see [[EitherValues]],[[FlatSpec]], [[Matchers]], [[OptionValues]],[[TableDrivenPropertyChecks]]
 */
abstract class BaseSpec
  extends FlatSpec with Matchers with TableDrivenPropertyChecks with EitherValues with OptionValues {}
