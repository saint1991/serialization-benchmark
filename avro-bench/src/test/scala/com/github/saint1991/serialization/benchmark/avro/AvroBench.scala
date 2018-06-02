package com.github.saint1991.serialization.benchmark.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
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
class AvroBench {

  final val Schema = Nobid.SCHEMA$
  val writer = new SpecificDatumWriter[Nobid](Schema)
  val reader = new SpecificDatumReader[Nobid](Schema)

  val dataset: Seq[Nobid] = DataSet.createDataset(DatasetSize)
  val encoded: Seq[Array[Byte]] = encode()
  decode()

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def encode(): Seq[Array[Byte]] = {
    dataset.map { nobid =>
      val ostream = new ByteArrayOutputStream()
      val encoder = EncoderFactory.get().binaryEncoder(ostream, null)
      writer.write(nobid, encoder)
      encoder.flush()
      ostream.toByteArray
    }
  }

  @JmhBenchmark @BenchmarkMode(Array(Mode.AverageTime))
  def decode(): Seq[Nobid] = {
    encoded.map { record =>
      val istream = new ByteArrayInputStream(record)
      val decoder = DecoderFactory.get.binaryDecoder(istream, null)
      reader.read(null, decoder)
    }
  }

}
