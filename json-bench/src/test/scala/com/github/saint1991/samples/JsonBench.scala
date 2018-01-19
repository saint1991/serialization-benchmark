package com.github.saint1991.samples

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import org.openjdk.jmh.annotations.{Benchmark => JmhBenchmark, BenchmarkMode, Mode, Scope, State}

@State(Scope.Benchmark)
class JsonBench {

  final val N = 100000
  val dataset = DataSet.createDataset(N)

  implicit val encoder = Encoder.enumEncoder(SpotType)
  implicit val decoder = Decoder.enumDecoder(SpotType)

  val encodedDataset = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[String] = {
    dataset.map(_.asJson.noSpaces)
  }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = {
    encodedDataset.map(str => parse(str).right.get.as[Nobid].right.get)
  }
}

