package fr.mistertie.lawnmower.cli.application

import fr.mistertie.lawnmower.cli.launcher.LawnMowerLauncher
import fr.mistertie.lawnmower.cli.launcher.LawnMowerLauncher.launch
import fr.mistertie.lawnmower.cli.parser.CommandLineParser
import fr.mistertie.lawnmower.cli.parser.CommandLineParser.parseArguments

import scala.Console.err
import scala.sys.exit

/**
 * Main class of the project that must remain an entry point with as little logic as possible. Delegate the arguments
 * parsing to a [[CommandLineParser]] and the application launch to a [[LawnMowerLauncher]].
 */
object LawnMowerApplication extends App {

  parseArguments(args) match {
    case Some(arguments) => launch(arguments.file).recover { case e: Throwable => err.println(e.getMessage); exit(2)}
    case None => exit(1)
  }

}
