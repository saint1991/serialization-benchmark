
scalaVersion in ThisBuild := "2.12.6"

lazy val jmhSettings = Seq(
  sourceDirectory in Jmh := (sourceDirectory in Test).value,
  classDirectory in Jmh := (classDirectory in Test).value,
  resourceDirectory in Jmh := (resourceDirectory in Test).value,
  dependencyClasspath in Jmh := (dependencyClasspath in Test).value,
  compile in Jmh := ((compile in Jmh) dependsOn (compile in Test)).value,
  run in Jmh := ((run in Jmh) dependsOn (compile in Jmh)).evaluated
)

lazy val commonSettings = Seq(
  scalacOptions := Seq(
    "-encoding", "UTF-8",
    "-deprecation",
    "-unchecked"
  )
)

lazy val common = (project in file("common"))
  .settings(commonSettings)
  .settings(
    name := "common",
    libraryDependencies ++= Seq(
      "com.github.pathikrit" %% "better-files" % "3.5.0",
      "log4j" % "log4j" % "1.2.17",
      "org.slf4j" % "slf4j-api" % "1.7.25",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
    )
  )

lazy val csvBench = (project in file("csv-bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "csv-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.csv.FileGen")
  )
  .dependsOn(common % "compile->compile")

lazy val circeBench = (project in file("circe-bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "json-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.circe.FileGen"),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.9.3",
      "io.circe" %% "circe-generic" % "0.9.3",
      "io.circe" %% "circe-parser" % "0.9.3"
    )
  )
  .dependsOn(common % "compile->compile")

lazy val jsoniterScalaBench = (project in file("jsoniter-scala-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "jsoniter-scala-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.jsoniter.FileGen"),
    libraryDependencies ++= Seq(
      "com.github.plokhotnyuk.jsoniter-scala" %% "macros" % "0.26.0"
    )
  )
  .dependsOn(common % "compile->compile")

lazy val msgpack4zBench = (project in file("msgpack4z-bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "msgpack4z-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.msgpack.msgpack4z.FileGen"),
    libraryDependencies ++= Seq(
      "com.github.xuwei-k" %% "msgpack4z-core" % "0.3.9",
      "com.github.xuwei-k" % "msgpack4z-java07" % "0.2.0"
    )
  )
  .dependsOn(common % "compile->compile")

lazy val msgpackJacksonBench = (project in file("msgpack-jackson-bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "msgpack-jackson-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.msgpack.jackson.FileGen"),
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.5",
      "org.msgpack" % "jackson-dataformat-msgpack" % "0.8.16"
    )
  )
  .dependsOn(common % "compile->compile")

lazy val avroBench = (project in file("avro-bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "avro-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.avro.FileGen"),
    libraryDependencies ++= Seq(
      "org.apache.avro" % "avro" % "1.8.2"
    )
  )
  .settings(
    avroSpecificSourceDirectory in Compile := file("schema/avro"),
    avroSpecificScalaSource in Compile := (sourceManaged in Compile).value,
    sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
  )
  .dependsOn(common % "compile->compile")

lazy val protoBench = (project in file("proto-bench"))
  .enablePlugins(ProtocPlugin, JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "protobuf-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.protobuf.FileGen"),
  )
  .settings( // for ScalaPB
    PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value),
    PB.protoSources in Compile := Seq(file("schema/protocol-buffers"))
  )
  .dependsOn(common % "compile->compile")

lazy val thriftBench = (project in file("thrift-bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(jmhSettings)
  .settings(
    name := "thrift-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.thrift.FileGen"),
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.11.0",
      "com.twitter" %% "scrooge-core" % "18.5.0" exclude("com.twitter", "libthrift")
    )
  )
  .settings(
    scroogeThriftIncludeFolders in Compile := Seq(file("schema/thrift")),
    scroogeThriftSourceFolder in Compile := file("schema/thrift"),
    scroogeThriftOutputFolder in Compile := (sourceManaged in Compile).value
  )
  .dependsOn(common % "compile->compile")
