package com.github.saint1991.serialization.benchmark.protobuf

import java.io.{FileOutputStream, OutputStream}

import scala.util.control.Exception._

import com.github.saint1991.serialization.benchmark.BenchmarkSettings.DatasetSize
import com.github.saint1991.serialization.benchmark.FileUtil
import com.github.saint1991.serialization.benchmark.protobuf.nobid.Nobid

object FileGen extends App {

  val dataset = DataSet.createDataset(DatasetSize)

  final val outFile = FileUtil.mkOutFile("nobid.protobuf")
  val out = new FileOutputStream(outFile.toJava)

  // write to file
  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, out)

  private def writeToFile(dataset: Seq[Nobid], file: OutputStream): Unit = {
    dataset.foreach(r => r.writeTo(file))
  }
}
