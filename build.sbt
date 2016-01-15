import sbt._

name := "Payment slip"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.6" % "test",
                            "joda-time" % "joda-time" % "2.9.1")

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
    libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.6" % "it,test",
      "joda-time" % "joda-time" % "2.9.1")
    // other settings here
  )