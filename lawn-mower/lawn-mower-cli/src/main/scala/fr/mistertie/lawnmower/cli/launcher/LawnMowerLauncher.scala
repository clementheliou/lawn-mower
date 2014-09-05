package fr.mistertie.lawnmower.cli.launcher

import java.io.File

import fr.mistertie.lawnmower.core.cardinalpoint.CardinalPoint
import fr.mistertie.lawnmower.core.lawn.Lawn
import fr.mistertie.lawnmower.core.mower.Mower
import fr.mistertie.lawnmower.core.point.Point
import fr.mistertie.lawnmower.parsing.common.reader.file.flat.FlatFileReader
import fr.mistertie.lawnmower.parsing.lawn.model.ParsedLawn

import scala.util.{Failure, Try}

/**
 * Object containing the launch logic allowing its easy testing. Thus, this code is extracted from the application's
 * main method that stays as simple as possible.
 */
object LawnMowerLauncher {

  /**
   * Launch the application from the given source file. 1) Parse file; 2) create lawn and mowers; 3) execute their
   * actions and print the resulting position.
   * @param sourceFile the source file to be used.
   * @return the launch result as a [[Try]] which type is [[Unit]].
   */
  def launch(sourceFile: File) = FlatFileReader(sourceFile).as[ParsedLawn] match {
    case Left(exception) => Failure(new IllegalArgumentException(exception))
    case Right(parsedLawn) => Try {
      val (lawnAbscissa, lawnOrdinate) = parsedLawn.topRightCorner
      val lawn = Lawn(lawnAbscissa, lawnOrdinate)

      parsedLawn.mowers map { mower => (createMowerFrom(mower.initialPosition, lawn),
        mower.actions)} foreach { case (mower, actions) => println(mower.exploreLawn(actions))}
    }
  }

  /**
   * Create a [[Mower]] from the given (valid) position tuple and lawn.
   * @param position the initial position as a tuple (abscissa, ordinate, orientation).
   * @param lawn the parent lawn.
   * @return the created mower.
   */
  private def createMowerFrom(position: (Int, Int, Char), lawn: Lawn) = {
    Mower(Point(position._1, position._2), CardinalPoint(position._3), lawn)
  }

}
