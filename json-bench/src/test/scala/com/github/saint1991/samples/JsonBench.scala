package com.github.saint1991.samples

import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import org.openjdk.jmh.annotations.{BenchmarkMode, Fork, Measurement, Mode, OutputTimeUnit, Scope, State, Warmup, Benchmark => JmhBenchmark}

@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgs = Array(
  "-server",
  "-Xms2g",
  "-Xmx2g",
  "-XX:NewSize=1g",
  "-XX:MaxNewSize=1g",
  "-XX:InitialCodeCacheSize=512m",
  "-XX:ReservedCodeCacheSize=512m",
  "-XX:+UseParallelGC",
  "-XX:-UseBiasedLocking",
  "-XX:+AlwaysPreTouch"
))
@OutputTimeUnit(TimeUnit.SECONDS)
class JsonBench {
  final val N = 100000
  val dataset = DataSet.createDataset(N)

  implicit val encoder = Encoder.enumEncoder(SpotType)
  implicit val decoder = Decoder.enumDecoder(SpotType)

  val encodedDataset = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[Array[Byte]] = {
    dataset.map(_.asJson.noSpaces.getBytes(StandardCharsets.UTF_8))
  }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = {
    encodedDataset.map(str => parse(new String(str, StandardCharsets.UTF_8)).right.get.as[Nobid].right.get)
  }
}

