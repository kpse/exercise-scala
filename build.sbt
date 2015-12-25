name := "Payment slip"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.6" % "test")

scalacOptions in Test ++= Seq("-Yrangepos")

mainClass in Compile := Some("com.myob.MainApp")
