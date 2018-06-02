package com.github.saint1991.serialization.benchmark.avro

import java.io.{BufferedOutputStream, FileOutputStream}

import scala.util.control.Exception.allCatch

import org.apache.avro.file.DataFileWriter
import org.apache.avro.specific.SpecificDatumWriter

import com.github.saint1991.serialization.benchmark.FileUtil

object FileGen extends App {

  final val Schema = Nobid.SCHEMA$
  val writer = new SpecificDatumWriter[Nobid](Schema)

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  val outFile = FileUtil.mkOutFile("nobid.avro")
  val out = new BufferedOutputStream(new FileOutputStream(outFile.toJava))
  val outFileWriter = new DataFileWriter[Nobid](writer)

  // write to file
  allCatch andFinally {
    outFileWriter.flush()
    outFileWriter.close()
  } apply writeToFile(dataset, outFileWriter)

  private def writeToFile(dataset: Seq[Nobid], writer: DataFileWriter[Nobid]): Unit = {
    outFileWriter.create(Schema, out)
    dataset.foreach { nobid => writer.append(nobid) }
  }

}
