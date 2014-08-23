package fr.mistertie.lawnmower.parsing.common.reader.file.flat

import fr.mistertie.lawnmower.parsing.common.reader.Reader

object FlatFileReader {
  def apply(path: String): Reader[Iterator[String]] = throw new UnsupportedOperationException

  // Reader { () => fromFile(path).getLines()}
}
