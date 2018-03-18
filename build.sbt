
val slf4jVersion = "1.7.21"

scalaVersion in ThisBuild := "2.12.4"

lazy val jmhSettings = Seq(
  sourceDirectory in Jmh := (sourceDirectory in Test).value,
  classDirectory in Jmh := (classDirectory in Test).value,
  resourceDirectory in Jmh := (resourceDirectory in Test).value,
  dependencyClasspath in Jmh := (dependencyClasspath in Test).value,
  compile in Jmh := ((compile in Jmh) dependsOn (compile in Test)).value,
  run in Jmh := ((run in Jmh) dependsOn (compile in Jmh)).evaluated
) ++ Seq(
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "slf4j-log4j12" % slf4jVersion
  )
)

lazy val csvBench = (project in file("csv-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "csv-bench",
    mainClass := Some("com.github.saint1991.samples.CsvBench")
  )

lazy val jsonBench = (project in file("json-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "json-bench",
    mainClass := Some("com.github.saint1991.samples.JsonBench"),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.9.2",
      "io.circe" %% "circe-generic" % "0.9.2",
      "io.circe" %% "circe-parser" % "0.9.2"
    )
  )

lazy val protoBench = (project in file("proto-bench"))
  .enablePlugins(ProtocPlugin, JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "protobuf-bench",
    mainClass := Some("com.github.saint1991.samples.ProtoBench"),
  )
  .settings( // for ScalaPB
    PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value),
    PB.protoSources in Compile := Seq(file("serialization/protocol-buffers"))
  )

lazy val thriftBench = (project in file("thrift-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "thrift-bench",
    mainClass := Some("com.github.saint1991.samples.ThriftBench"),
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.11.0",
      "com.twitter" %% "scrooge-core" % "18.3.0" exclude("com.twitter", "libthrift")
    )
  )
  .settings(
    scroogeThriftIncludeFolders in Compile := Seq(file("serialization/thrift")),
    scroogeThriftSourceFolder in Compile := file("serialization/thrift"),
    scroogeThriftOutputFolder in Compile := (sourceManaged in Compile).value
  )

lazy val avroBench = (project in file("avro-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "avro-bench",
    mainClass := Some("com.github.saint1991.samples.AvroBench"),
    libraryDependencies += "org.apache.avro" % "avro" % "1.8.2"
  )
  .settings(
    avroSpecificSourceDirectory in Compile := file("serialization/avro"),
    avroSpecificScalaSource in Compile := (sourceManaged in Compile).value,
    sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
  )

lazy val msgPackBench = (project in file("msgpack-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "msgpack-bench",
    mainClass := Some("com.github.saint1991.samples.MsgPackBench"),
    libraryDependencies += "org.msgpack" %% "msgpack-scala" % "0.8.13"
  )
