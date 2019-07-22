# akka-persistence-json-serializaer-example
This example demonstrate how to setup akka jackson JSON serialization integration

_update `build.sbt` file:_

```scala
scalaVersion := "2.13.0"
val jacksonVersion = "2.9.9"
libraryDependencies ++= Seq(
  "com.typesafe.akka"            %% "akka-persistence"     % "2.5.23"      ,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "com.fasterxml.jackson.core"   %  "jackson-databind"     % jacksonVersion,
  // ...
)
```

_create `src/main/scala/my/app/MySerializer.scala` file:_

```scala
import akka.serialization.JSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object MySerializer {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  // other optional jackson configurations...
}

class MySerializer extends JSerializer {
  import MySerializer._
  override protected def fromBinaryJava(bytes: Array[Byte], manifest: Class[_]): AnyRef = mapper.readValue(bytes)
  override def identifier: Int = 1234567
  override def toBinary(o: AnyRef): Array[Byte] = mapper.writeValueAsBytes(o)
  override def includeManifest: Boolean = false
}
```

_update `src/main/resources/application.conf` file:_

```scala
akka {
  actor {
    serialize-creators = on
    enable-additional-serialization-bindings = on

    serializers {
      json = "my.app.MySerializer"
    }

    serialization-bindings {
      "my.app.MyClass1" = json
      "my.app.MyClass2" = json
      // ...
      "my.app.MyClassN" = json
    }
  }
}
```

_build, run and test current sample:_

```bash
./sbtw clean "runMain com.github.daggerok.akka.persistence.SnapshotExample"
# ...
cat target/persistence-snapshots/snapshot-*
```

## generated (original) README.md 
This tutorial contains examples that illustrate a subset of[Akka Persistence](http://doc.akka.io/docs/akka/2.5/scala/persistence.html) features.

- persistent actor
- persistent actor snapshots
- persistent actor recovery

Custom storage locations for the journal and snapshots can be defined in [application.conf](src/main/resources/application.conf).

### Persistent actor

[PersistentActorExample.scala](src/main/scala/com/github/daggerok/akka/persistence/PersistentActorExample.scala) is described in detail in the [Event sourcing](http://doc.akka.io/docs/akka/2.5/scala/persistence.html#event-sourcing) section of the user documentation. With every application run, the `ExamplePersistentActor` is recovered from events stored in previous application runs, processes new commands, stores new events and snapshots and prints the current persistent actor state to `stdout`.

To run this example, type `sbt "runMain sample.persistence.PersistentActorExample"`.

### Persistent actor snapshots

[SnapshotExample.scala](src/main/scala/com/github/daggerok/akka/persistence/SnapshotExample.scala) demonstrates how persistent actors can take snapshots of application state and recover from previously stored snapshots. Snapshots are offered to persistent actors at the beginning of recovery, before any messages (younger than the snapshot) are replayed.

To run this example, type `sbt "runMain sample.persistence.SnapshotExample"`. With every run, the state offered by the most recent snapshot is printed to `stdout`, followed by the updated state after sending new persistent messages to the persistent actor.

### Persistent actor recovery

[PersistentActorFailureExample.scala](src/main/scala/com/github/daggerok/akka/persistence/PersistentActorFailureExample.scala) shows how a persistent actor can throw an exception, restart and restore the state by replaying the events.

To run this example, type `sbt "runMain sample.persistence.PersistentActorFailureExample"`.
