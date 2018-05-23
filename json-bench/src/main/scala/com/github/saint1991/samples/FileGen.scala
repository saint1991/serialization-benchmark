package com.github.saint1991.samples

import java.io.{File, FileOutputStream, PrintWriter}

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

import scala.util.control.Exception._

object FileGen extends App {

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  implicit val encoder = Encoder.enumEncoder(SpotType)

  final val outDir = new File("out")
  if (!outDir.exists()) outDir.mkdir()
  final val outFile = new File("out/nobids.json")
  outFile.createNewFile()

  val out = new PrintWriter(new FileOutputStream(outFile))

  val encodedDatasets = encode(dataset)
  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(encodedDatasets, out)

  private def encode(nobids: Seq[Nobid]): Seq[String] = {
    nobids.map(_.asJson.noSpaces)
  }

  private def writeToFile(encoded: Seq[String], out: PrintWriter) = {
    encoded.foreach { r =>
      out.println(r)
    }
  }
}

