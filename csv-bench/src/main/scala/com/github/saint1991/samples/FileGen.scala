package com.github.saint1991.samples

import java.io.{File, FileOutputStream, PrintWriter}

import scala.util.control.Exception._

object FileGen extends App {
  import Nobid._

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  val encodedDatasets = encode(dataset)

  // write to file
  final val outFile = new File("out/nobids.csv")
  outFile.createNewFile()

  val out = new PrintWriter(new FileOutputStream(outFile))

  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(encodedDatasets, out)

  private def encode(dataset: Seq[Nobid]): Seq[String] = {
    dataset.map(toCsv)
  }

  private def writeToFile(dataset: Seq[String], file: PrintWriter) = {
    dataset.foreach{ r =>
      file.println(r)
    }
  }
}
