package fr.mistertie.lawnmower.parsing.common.converter

/**
 * Converter from the given input type (I) to the output one (O).
 * Inspired by David Galichet's talk (@dgalichet) at Paris Scala User Group.
 * @see http://fr.slideshare.net/dgalichet/writing-dsl-with-applicative-functors
 * @tparam I the input type.
 * @tparam O the output type.
 */
trait Converter[I, O] {

  /**
   * Convert the given instance of type I to an [[Either]] of type O.
   * @param input the content to be converted.
   * @return the conversion result.
   */
  def convert(input: I): Either[String, O]
}

