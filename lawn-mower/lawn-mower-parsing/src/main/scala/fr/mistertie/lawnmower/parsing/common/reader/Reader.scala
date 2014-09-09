package fr.mistertie.lawnmower.parsing.common.reader

import fr.mistertie.lawnmower.parsing.common.converter.Converter

/**
 * Reader of T instances from the provided function.
 * Inspired by David Galichet's talk (@dgalichet) at Paris Scala User Group.
 * @see http://fr.slideshare.net/dgalichet/writing-dsl-with-applicative-functors
 * @param reading the reading process to be performed.
 * @tparam T the read type.
 */
class Reader[T](val reading: () => Either[String, T]) {

  /**
   * Convert the read value to an [[Either]] of the given output type O. An implicit converter from type T to O is
   * expected and is triggered if the reading process is successful. Return the reading failure message otherwise.
   * @param converter the implicit converter used for conversion.
   * @tparam O the output type.
   * @return the converted value if reading is successful.
   */
  def as[O](implicit converter: Converter[T, O]) = reading() match {
    case Right(readingResult) => converter.convert(readingResult)
    case Left(errorMessage) => Left(errorMessage)
  }

}
