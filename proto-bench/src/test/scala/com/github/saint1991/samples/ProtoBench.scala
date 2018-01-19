package com.github.saint1991.samples

import com.github.saint1991.samples.nobid.Nobid
import org.openjdk.jmh.annotations.{Benchmark => JmhBenchmark, BenchmarkMode, Mode, Scope, State}

@State(Scope.Benchmark)
class ProtoBench {

  final val N = 100000
  val dataset = DataSet.createDataset(N)

  val encodedDataset = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[Array[Byte]] = {
    dataset.map(_.toByteArray)
  }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = {
    encodedDataset.map(Nobid.parseFrom)
  }
}
