package fr.mistertie.lawnmower.parsing.common.converter

trait Converter[I, O] {
  def convert(readContent: Either[String, I]): Either[String, O]
}

