package fr.mistertie.lawnmower.parsing.common.reader

import fr.mistertie.lawnmower.parsing.common.converter.Converter

class Reader[T] private(val reading: () => Either[String, T]) {
  def as[O](implicit converter: Converter[T, O]): Either[String, O] = throw new UnsupportedOperationException

  //converter.convert(reading())
}
