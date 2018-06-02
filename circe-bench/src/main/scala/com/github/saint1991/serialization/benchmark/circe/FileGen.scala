package com.github.saint1991.serialization.benchmark.circe

import java.io.{BufferedOutputStream, FileOutputStream, PrintWriter}

import scala.util.control.Exception._

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

import com.github.saint1991.serialization.benchmark.dataset._
import com.github.saint1991.serialization.benchmark.FileUtil

object FileGen extends App {

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  implicit val encoder: Encoder[SpotType.Value] = Encoder.enumEncoder(SpotType)

  final val outFile = FileUtil.mkOutFile("nobid-circe.json")
  val out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(outFile.toJava)))

  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, out)

  private def writeToFile(dataset: Seq[Nobid], out: PrintWriter): Unit =
    dataset.foreach { r =>
      val record = r.asJson.noSpaces
      out.println(record)
    }

}
