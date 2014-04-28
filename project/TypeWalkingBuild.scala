import com.typesafe.sbt.SbtAtmos
import com.typesafe.sbt.SbtAtmos.Atmos
import com.typesafe.sbt.SbtAtmos.AtmosKeys.{traceable,sampling}
import sbt._
import sbt.Keys._
import spray.revolver.RevolverPlugin._

object TypeWalkingBuild extends Build {
  val AkkaVersion = "2.2.1"

  lazy val typewalking = Project(
    id       = "typewalking",
    base     = file("."),
    settings =
      Project.defaultSettings ++
      Revolver.settings ++
      SbtAtmos.atmosSettings ++
      Seq(
        name                          := "typewalking",
        organization                  := "bozzy",
        version                       := "0.1-SNAPSHOT",
        scalaVersion                  := "2.10.2",
        mainClass in Revolver.reStart := Some("bozzy.VakaApp"),
        initialCommands               += "import bozzy._",
        scalacOptions                 += "-feature",
        traceable in Atmos            := Seq("*" → false),
        libraryDependencies          ++= Seq(
          "com.typesafe.akka" %% "akka-actor"                 % AkkaVersion,
          "com.typesafe.akka" %% "akka-channels-experimental" % AkkaVersion,
          "com.chuusai"        % "shapeless"                  % "2.0.0-M1" cross CrossVersion.full,
          "org.spire-math"    %% "spire"                      % "0.6.0"),
        resolvers ++= Seq(
          "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/",
          "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/")))
    .configs(SbtAtmos.Atmos)
}
