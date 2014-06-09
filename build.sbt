name := """financeiro-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  cache,
  javaWs,
  "org.hibernate" % "hibernate-core" % "4.3.5.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  "org.hibernate" % "hibernate-envers" % "4.3.5.Final"
)

resolvers += "MavenJboss" at "https://repository.jboss.org/nexus/"