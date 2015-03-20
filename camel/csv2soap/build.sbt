name := "camel"

organization := "integration"

version := "1.0.0-SNAPSHOT"

description := "Camel routes"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

//// COMMON ///
libraryDependencies ++= Seq(
   "log4j" % "log4j" % "1.2.17",
   "org.slf4j" % "slf4j-log4j12" % "1.7.5"
)

//// CAMEL ///

val camelVersion = settingKey[String]("Camel version")

camelVersion := "2.10.0"

libraryDependencies ++= Seq(
   "org.apache.camel" % "camel-core"      % camelVersion.value,
   "org.apache.camel" % "camel-csv"       % camelVersion.value,
   "org.apache.camel" % "camel-test"      % camelVersion.value % "test"
)

//// TEST ////

libraryDependencies ++= Seq(
   "junit" % "junit" % "4.11" % "test",
   "com.novocode" % "junit-interface" % "0.9" % "test"
)

///// SBT Eclipse plugin ///

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
