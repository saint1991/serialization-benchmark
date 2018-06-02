package com.github.saint1991.serialization.benchmark

import java.util.concurrent.TimeUnit

object BenchmarkSettings {
  final val WarmUpIteration = 20
  final val Iteration = 20
  final val TUnit = TimeUnit.MILLISECONDS
  final val DatasetSize = 100000
}
