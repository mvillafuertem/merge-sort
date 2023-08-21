import _root_.scala.{Console => csl}

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / onLoad               := {
  val GREEN = csl.GREEN
  val RESET = csl.RESET
  println(s"""$GREEN
             |$GREEN        ███╗   ███╗ ███████╗ ██████╗   ██████╗  ███████╗       ███████╗  ██████╗  ██████╗  ████████╗
             |$GREEN        ████╗ ████║ ██╔════╝ ██╔══██╗ ██╔════╝  ██╔════╝       ██╔════╝ ██╔═══██╗ ██╔══██╗ ╚══██╔══╝
             |$GREEN        ██╔████╔██║ █████╗   ██████╔╝ ██║  ███╗ █████╗  █████╗ ███████╗ ██║   ██║ ██████╔╝    ██║   
             |$GREEN        ██║╚██╔╝██║ ██╔══╝   ██╔══██╗ ██║   ██║ ██╔══╝  ╚════╝ ╚════██║ ██║   ██║ ██╔══██╗    ██║   
             |$GREEN        ██║ ╚═╝ ██║ ███████╗ ██║  ██║ ╚██████╔╝ ███████╗       ███████║ ╚██████╔╝ ██║  ██║    ██║   
             |$GREEN        ╚═╝     ╚═╝ ╚══════╝ ╚═╝  ╚═╝  ╚═════╝  ╚══════╝       ╚══════╝  ╚═════╝  ╚═╝  ╚═╝    ╚═╝   
             |$RESET        v.${version.value}
             |""".stripMargin)
  (Global / onLoad).value
}

ThisBuild / scalaVersion      := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "merge-sort",
    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.2.14"),
    Compile / compile / wartremoverErrors ++= Seq(
      Wart.AsInstanceOf,
      Wart.DefaultArguments,
      Wart.EitherProjectionPartial,
      Wart.IsInstanceOf,
      Wart.IterableOps,
      Wart.NonUnitStatements,
      Wart.Null,
      Wart.OptionPartial,
      Wart.Return,
      Wart.StringPlusAny,
      // Wart.Throw,
      Wart.TryPartial,
      // Wart.Var
    ),
    commandsMessage
  )

def commandsMessage: Def.Setting[String] = onLoadMessage := {
  def header(text: String): String                = s"${csl.BOLD}${csl.MAGENTA}$text${csl.RESET}"
  def cmd(text: String, description: String = "") = f"${csl.GREEN}> ${csl.CYAN}$text%50s $description${csl.RESET}"

  s"""|${header("sbt")}: commands
      |${cmd("show test:definedTests", "- List tests")}
      |${cmd("test", "- Run tests")}
      |${cmd("testOnly *MergeSortFTest", "- Runs selected class tests")}
      |${cmd("testOnly *MergeSortFTest -- -z \"Vector\"", "- Runs selected tests")}
      |${cmd("scalafmt", "- Formats source files using scalafmt")}
      """.stripMargin
}
