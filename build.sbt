organization := "com.github.daggerok.akka"
name := "akka-persistence-json-serializaer-example"

val jacksonVersion = "2.9.9"
scalaVersion := "2.13.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka"            %% "akka-persistence"     % "2.5.23"      ,
  "org.iq80.leveldb"             %  "leveldb"              % "0.7"         ,
  "org.fusesource.leveldbjni"    %  "leveldbjni-all"       % "1.8"         ,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "com.fasterxml.jackson.core"   %  "jackson-databind"     % jacksonVersion,
)

licenses := Seq(
  ("MIT", url("https://github.com/daggerok/akka-persistence-json-serializaer-example/blob/master/LICENSE"))
)
