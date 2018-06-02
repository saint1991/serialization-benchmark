package com.github.saint1991.serialization.benchmark.msgpack.msgpack4z

import java.io.{BufferedOutputStream, FileOutputStream, OutputStream}

import scala.util.control.Exception._

import msgpack4z._

import com.github.saint1991.serialization.benchmark.BenchmarkSettings._
import com.github.saint1991.serialization.benchmark.dataset.DataSet
import com.github.saint1991.serialization.benchmark.FileUtil
import com.github.saint1991.serialization.benchmark.FileUtil.NewLineBytes
import com.github.saint1991.serialization.benchmark.msgpack.msgpack4z.Codec._

object FileGen extends App {

  val dataset = DataSet.createDataset(DatasetSize)

  final val outFile = FileUtil.mkOutFile("nobid-msgpack4z.msgpack")
  val out = new BufferedOutputStream(new FileOutputStream(outFile.toJava))

  // write to file
  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, codec, out)

  private def writeToFile[T](dataset: Seq[T], codec: MsgpackCodec[T], file: OutputStream): Unit =
    dataset.foreach { r =>
      val packer = new Msgpack07Packer()
      val bytes = codec.toBytes(r, packer)
      file.write(bytes)
      file.write(NewLineBytes)
    }

}
