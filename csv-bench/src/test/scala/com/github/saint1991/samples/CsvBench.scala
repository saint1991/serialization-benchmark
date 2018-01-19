package com.github.saint1991.samples

import org.openjdk.jmh.annotations.{BenchmarkMode, Mode, Scope, State, Benchmark => JmhBenchmark}

@State(Scope.Benchmark)
class CsvBench {
  import Nobid._

  final val N = 100000
  val dataset = DataSet.createDataset(N)

  val encodedDataset = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[String] = {
    dataset.map(toCsv)
  }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = {
    encodedDataset.map(fromCsv)
  }
}
