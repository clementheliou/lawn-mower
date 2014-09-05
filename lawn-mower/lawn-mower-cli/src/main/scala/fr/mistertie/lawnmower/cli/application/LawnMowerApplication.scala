package fr.mistertie.lawnmower.cli.application

import fr.mistertie.lawnmower.cli.launcher.LawnMowerLauncher
import fr.mistertie.lawnmower.cli.launcher.LawnMowerLauncher.launch
import fr.mistertie.lawnmower.cli.parser.CommandLineParser
import fr.mistertie.lawnmower.cli.parser.CommandLineParser.parseArguments

import scala.sys.exit
import scala.util.Try

/**
 * Main class of the project that must remain an entry point with as little logic as possible.
 */
object LawnMowerApplication {

  /**
   * Entry point of the project parsing the arguments and starting the application. Delegate the arguments parsing to
   * a [[CommandLineParser]] and the application launch to a [[LawnMowerLauncher]].
   * @param args the application's arguments
   */
  def main(args: Array[String]) {
    parseArguments(args) match {
      case Some(arguments) => Try(launch(arguments.file)).recover { case _ => exit(2)}
      case None => exit(1)
    }

  }
}
