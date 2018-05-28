package com.github.saint1991.serialization.benchmark

import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import io.circe.parser._
import org.openjdk.jmh.annotations.{BenchmarkMode, Fork, Measurement, Mode, OutputTimeUnit, Scope, State, Warmup, Benchmark => JmhBenchmark}

import com.github.saint1991.serialization.benchmark.dataset._

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
class CirceBench {
  final val N = 100000
  val dataset: Seq[Nobid] = DataSet.createDataset(N)

  implicit val encoder: Encoder[SpotType.Value] = Encoder.enumEncoder(SpotType)
  implicit val decoder: Decoder[SpotType.Value] = Decoder.enumDecoder(SpotType)

  val encodedDataset: Seq[Array[Byte]] = encode()
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

