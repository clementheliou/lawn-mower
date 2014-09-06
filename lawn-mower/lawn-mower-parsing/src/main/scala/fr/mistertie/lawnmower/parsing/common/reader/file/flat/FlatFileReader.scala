package fr.mistertie.lawnmower.parsing.common.reader.file.flat

import java.io.File

import fr.mistertie.lawnmower.parsing.common.reader.Reader

import scala.io.BufferedSource
import scala.io.Source.fromFile
import scala.util.control.NonFatal

/**
 * Flat file reader returning a [[List]]of read lines. Reading is performed from the source [[File]].
 */
object FlatFileReader {

  /**
   * Well-known apply method allowing an easy use of the underlying [[Reader]]'s constructor.
   * @param file the file to be read.
   * @return a reader returning the file lines in case of successful reading. The failure message otherwise.
   */
  def apply(file: File): Reader[List[String]] = new Reader[List[String]]({ () =>
    var source: Option[BufferedSource] = None

    try {
      source = Option(fromFile(file))
      Right(source.get.getLines().toList)
    } catch {
      case NonFatal(e) => Left(e.toString)
    } finally {
      source.foreach(_.close())
    }

  })

}
