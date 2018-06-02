package com.github.saint1991.serialization.benchmark.dataset

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object SpotType extends Enumeration {
  final val A = Value(1)
  final val S = Value(2)
}

case class Spot(
  id: Int,
  `type`: SpotType.Value
)

case class Nobid(
  adnwId: Int,
  appName: String,
  auctionId: String,
  host: String,
  loggedAt: String,
  mId: Int,
  nbr: Int,
  page: String,
  resTime: Int,
  spot: Spot,
  history: Seq[String],
  tags: Map[String, String]
)

object DataSet {
  def createDataset(n: Int): Seq[Nobid] = Seq.fill(n) {
    Nobid(
      adnwId = 12345,
      appName = "sampleApp",
      auctionId = "14241c7f-7db1-4bcd-a3f7-82885e08e7ec",
      host = "prd-dsp03",
      loggedAt = "2017-06-30 09:07:37.677",
      mId = 234,
      nbr = 6260,
      page = "http://diamond.jp/articles/-/15434",
      resTime = 4,
      spot = Spot(
        id = 2406,
        `type` = SpotType.S
      ),
      history = Seq(
        "a",
        "b",
        "c"
      ),
      tags = Map(
        "media" -> "facebook",
        "ssp" -> "google"
      )
    )
  }
}
