package com.github.saint1991.samples

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.openjdk.jmh.annotations.{BenchmarkMode, Mode, Scope, State, Benchmark => JmhBenchmark}

@State(Scope.Benchmark)
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
