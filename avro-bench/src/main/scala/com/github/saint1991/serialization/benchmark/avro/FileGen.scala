package com.github.saint1991.serialization.benchmark.avro

import java.io.File

import scala.util.control.Exception.allCatch

import org.apache.avro.Schema
import org.apache.avro.file.DataFileWriter
import org.apache.avro.specific.SpecificDatumWriter

import com.github.saint1991.serialization.benchmark.FileUtil

object FileGen extends App {

  final val Schema = Nobid.SCHEMA$
  val writer = new SpecificDatumWriter[Nobid](Schema)

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  val outFile = FileUtil.mkOutFile("nobid.avro")
  val outFileWriter = new DataFileWriter[Nobid](writer)

  // write to file
  writeToFile(dataset, Schema, outFile.toJava, outFileWriter)

  private def writeToFile(dataset: Seq[Nobid], writerSchema: Schema, file: File, writer: DataFileWriter[Nobid]): Unit = allCatch andFinally {
    writer.flush()
    writer.close()
  } apply {
    writer.create(writerSchema, file)
    dataset.foreach { nobid =>
      writer.append(nobid)
    }
  }
}
