name := "RewardPlatform"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.31",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test"
)

