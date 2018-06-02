package com.github.saint1991.serialization.benchmark.jsoniter

import java.io.{BufferedOutputStream, FileOutputStream}

import scala.util.control.Exception._

import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._

import com.github.saint1991.serialization.benchmark.dataset._
import com.github.saint1991.serialization.benchmark.FileUtil

object FileGen extends App {

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  final val outFile = FileUtil.mkOutFile("nobid-jsoniter.json")

  implicit val codec: JsonValueCodec[Nobid] = JsonCodecMaker.make[Nobid](CodecMakerConfig())
  val out = new BufferedOutputStream(new FileOutputStream(outFile.toJava))

  allCatch andFinally {
    out.flush()
    out.close()
  } apply dataset.foreach(x => writeToStream(x, out))
}
