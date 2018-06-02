package com.github.saint1991.serialization.benchmark.thrift

import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.{TMemoryBuffer, TMemoryInputTransport}
import org.openjdk.jmh.annotations.{BenchmarkMode, Fork, Measurement, Mode, OutputTimeUnit, Scope, State, Warmup, Benchmark => JmhBenchmark}

import com.github.saint1991.serialization.benchmark.BenchmarkSettings._

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
class ThriftBench {

  val dataset: Seq[Nobid] = DataSet.createDataset(DatasetSize)

  val encodedDataset: Seq[Array[Byte]] = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[Array[Byte]] = {
    dataset.map { r =>
      val buf = new TMemoryBuffer(0)
      val outProtocol = new TCompactProtocol(buf)
      r.write(outProtocol)
      buf.getArray
    }
  }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = {
    encodedDataset.map { r =>
      val buf = new TMemoryInputTransport(r)
      val inProtocol = new TCompactProtocol(buf)
      Nobid.decode(inProtocol)
    }
  }
}
