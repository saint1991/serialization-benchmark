package com.github.saint1991.samples

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.concurrent.TimeUnit

import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
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
class AvroBench {

  final val Schema = Nobid.SCHEMA$
  val writer = new SpecificDatumWriter[Nobid](Schema)
  val reader = new SpecificDatumReader[Nobid](Schema)

  final val N = 100000
  val dataset = DataSet.createDataset(N)
  val encoded = encode()

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
