package com.github.saint1991.serialization.benchmark

import java.io.{File, FileOutputStream}

import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._

import com.github.saint1991.serialization.benchmark.dataset._

object FileGen extends App {

  final val N = 10000
  val dataset = DataSet.createDataset(N)

  final val outDir = new File("out")
  if (!outDir.exists()) outDir.mkdir()
  final val outFile = new File("out/nobids.json")
  outFile.createNewFile()

  implicit val codec: JsonValueCodec[Nobid] = JsonCodecMaker.make[Nobid](CodecMakerConfig())
  val out = new FileOutputStream(outFile)
  try dataset.foreach(x => writeToStream(x, out)) finally out.close()
}

