package com.github.saint1991.serialization.benchmark.csv

import java.io.{FileOutputStream, PrintWriter}

import scala.util.control.Exception._

import com.github.saint1991.serialization.benchmark.BenchmarkSettings.DatasetSize
import com.github.saint1991.serialization.benchmark.FileUtil
import com.github.saint1991.serialization.benchmark.dataset._

object FileGen extends App {
  import Csv._

  val dataset = DataSet.createDataset(DatasetSize)

  // write to file
  final val outFile = FileUtil.mkOutFile("nobid.csv")
  val out = new PrintWriter(new FileOutputStream(outFile.toJava))

  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, out)

  private def writeToFile(dataset: Seq[Nobid], file: PrintWriter): Unit =
    dataset.foreach{ r => file.println(toCsv(r)) }
}
