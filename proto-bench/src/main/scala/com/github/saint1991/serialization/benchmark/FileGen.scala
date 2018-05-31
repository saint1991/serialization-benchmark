package com.github.saint1991.serialization.benchmark

import java.io.{File, FileOutputStream, OutputStream}

import scala.util.control.Exception._

import com.github.saint1991.serialization.benchmark.nobid.Nobid

object FileGen extends App {

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  final val outDir = new File("out")
  if (!outDir.exists()) outDir.mkdir()
  final val outFile = new File("out/nobids.protobuf")
  outFile.createNewFile()

  val out = new FileOutputStream(outFile)

  // write to file
  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, out)

  private def writeToFile(dataset: Seq[Nobid], file: OutputStream): Unit = {
    dataset.foreach(r => r.writeTo(file))
  }
}
