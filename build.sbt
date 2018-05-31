
val slf4jVersion = "1.7.21"

scalaVersion in ThisBuild := "2.12.6"

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

lazy val common = (project in file("common"))
  .settings(
    name := "common"
  )

lazy val csvBench = (project in file("csv-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "csv-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.FileGen")
  )
  .dependsOn(common % "compile->compile")

lazy val circeBench = (project in file("circe-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "json-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.FileGen"),
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
    mainClass := Some("com.github.saint1991.serialization.benchmark.FileGen"),
    libraryDependencies ++= Seq(
      "com.github.plokhotnyuk.jsoniter-scala" %% "macros" % "0.26.0"
    )
  )
  .dependsOn(common % "compile->compile")

lazy val avroBench = (project in file("avro-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "avro-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.FileGen"),
    libraryDependencies += "org.apache.avro" % "avro" % "1.8.2"
  )
  .settings(
    avroSpecificSourceDirectory in Compile := file("schema/avro"),
    avroSpecificScalaSource in Compile := (sourceManaged in Compile).value,
    sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
  )

lazy val protoBench = (project in file("proto-bench"))
  .enablePlugins(ProtocPlugin, JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "protobuf-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.FileGen"),
  )
  .settings( // for ScalaPB
    PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value),
    PB.protoSources in Compile := Seq(file("schema/protocol-buffers"))
  )

lazy val thriftBench = (project in file("thrift-bench"))
  .enablePlugins(JmhPlugin)
  .settings(jmhSettings)
  .settings(
    name := "thrift-bench",
    mainClass := Some("com.github.saint1991.serialization.benchmark.FileGen"),
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
