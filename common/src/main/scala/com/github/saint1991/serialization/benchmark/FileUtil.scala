package com.github.saint1991.serialization.benchmark

import better.files._

object FileUtil {

  final val OutDir = "out"
  final val NewLineBytes = "\n".getBytes

  def mkOutFile(name: String): File = (OutDir / name)
      .createIfNotExists(createParents = true)
      .clear()
}
