package com.github.saint1991.serialization.benchmark

import java.io._

import scala.util.control.Exception._

import org.apache.thrift.protocol.{TCompactProtocol, TProtocol}
import org.apache.thrift.transport.TIOStreamTransport

object FileGen extends App {

  final val N = 10000
  final val BufferSize = 32 * 1024
  val dataset = DataSet.createDataset(N)

  val outFile = new File("out/nobids.thrift")
  outFile.createNewFile()

  val out = new BufferedOutputStream(new FileOutputStream(outFile))
  val outProtocol = new TCompactProtocol(new TIOStreamTransport(out))

  allCatch andFinally out.close() apply writeToFile(dataset, out, outProtocol)

  private def writeToFile(dataset: Seq[Nobid], out: BufferedOutputStream, outProtocol: TProtocol): Unit = {
    dataset.foreach { r =>
      r.write(outProtocol)
      out.flush()
    }
  }
}
