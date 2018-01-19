package com.github.saint1991.samples

import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.{TMemoryBuffer, TMemoryInputTransport}
import org.openjdk.jmh.annotations.{Benchmark => JmhBenchmark, BenchmarkMode, Mode, Scope, State}

@State(Scope.Benchmark)
class ThriftBench {

  final val N = 100000
  val dataset = DataSet.createDataset(N)

  val encodedDataset = encode()
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
