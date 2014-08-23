package fr.mistertie.lawnmower.parsing.lawn.converter

import fr.mistertie.lawnmower.parsing.common.converter.Converter
import fr.mistertie.lawnmower.parsing.lawn.model.ParsedLawn

object LawnConverter extends Converter[Iterator[String], ParsedLawn] {
  def convert(readContent: Either[String, Iterator[String]]): Either[String,
    ParsedLawn] = throw new UnsupportedOperationException
}
