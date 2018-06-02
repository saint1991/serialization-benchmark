package com.github.saint1991.serialization.benchmark

import better.files._

object FileUtil {

  final val OutDir = "out"

  def mkOutFile(name: String): File = (OutDir / name)
      .createIfNotExists(createParents = true)
      .clear()
}
