package fr.mistertie.lawnmower.parsing.common.reader.file.flat

import fr.mistertie.lawnmower.parsing.common.reader.Reader

import scala.io.Source.fromFile
import scala.util.control.NonFatal

/**
 * Flat file reader returning an [[Iterator]] of read lines. Reading is performed from the file path.
 */
object FlatFileReader {

  /**
   * Well-known apply method allowing an easy use of the underlying [[Reader]]'s constructor.
   * @param path the path of the file to be read.
   * @return a reader returning the file lines in case of successful reading. The failure message otherwise.
   */
  def apply(path: String): Reader[Iterator[String]] = new Reader[Iterator[String]]({ () =>
    val source = fromFile(path)

    try {
      Right(source.getLines())
    } catch {
      case NonFatal(e) => Left(e.getMessage)
    } finally {
      source.close()
    }

  })

}
