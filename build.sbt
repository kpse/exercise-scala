import sbt._

name := "Payment slip"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.6" % "test")

scalacOptions in Test ++= Seq("-Yrangepos")

mainClass in Compile := Some("com.myob.MainApp")

lazy val commonSettings = Seq(
  scalaVersion := "2.11.4",
  organization := "com.myob"
)

lazy val root = (project in file(".")).
  configs(IntegrationTest).
  settings(commonSettings: _*).
  settings(Defaults.itSettings: _*).
  settings(
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.6" % "it,test"
    // other settings here
  )