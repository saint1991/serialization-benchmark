package com.github.saint1991.serialization.benchmark.msgpack.msgpack4z

import msgpack4z.{Msgpack07Packer, Msgpack07Unpacker}
import org.openjdk.jmh.annotations.{BenchmarkMode, Fork, Measurement, Mode, OutputTimeUnit, Scope, State, Warmup, Benchmark => JmhBenchmark}

import com.github.saint1991.serialization.benchmark.BenchmarkSettings.{DatasetSize, Iteration, TUnit, WarmUpIteration}
import com.github.saint1991.serialization.benchmark.dataset.{DataSet, Nobid}
import com.github.saint1991.serialization.benchmark.msgpack.msgpack4z.Codec._

@State(Scope.Thread)
@Warmup(iterations = WarmUpIteration, time = 1, timeUnit = TUnit)
@Measurement(iterations = Iteration, time = 1, timeUnit = TUnit)
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
@OutputTimeUnit(TUnit)
class Msgpack4zBench {

  val dataset: Seq[Nobid] = DataSet.createDataset(DatasetSize)
  val encodedDataset: Seq[Array[Byte]] = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[Array[Byte]] = dataset.map { r => codec.toBytes(r, new Msgpack07Packer()) }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = encodedDataset.map { bytes =>
    codec.unpack(Msgpack07Unpacker.defaultUnpacker(bytes)).getOrElse(throw new Exception("error on unpacking"))
  }
}
