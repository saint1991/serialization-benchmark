package com.github.saint1991.serialization.benchmark.thrift

import java.io._
import java.nio.ByteBuffer

import scala.util.control.Exception._

import org.apache.thrift.protocol.{TCompactProtocol, TProtocol}
import org.apache.thrift.transport.TIOStreamTransport

import com.github.saint1991.serialization.benchmark.FileUtil

object FileGen extends App {

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  val outFile = FileUtil.mkOutFile("nobid.thrift")
  val out = new BufferedOutputStream(new FileOutputStream(outFile.toJava))
  val outProtocol = new TCompactProtocol(new TIOStreamTransport(out))

  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, out, outProtocol)

  private def writeToFile(dataset: Seq[Nobid], out: OutputStream, outProtocol: TProtocol): Unit =
    dataset.foreach { r =>
      r.write(outProtocol)
      outProtocol.writeBinary(ByteBuffer.wrap(FileUtil.NewLineBytes))
    }

}
