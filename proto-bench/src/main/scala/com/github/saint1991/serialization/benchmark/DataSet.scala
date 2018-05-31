package com.github.saint1991.serialization.benchmark

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import com.github.saint1991.serialization.benchmark.nobid.Nobid
import com.github.saint1991.serialization.benchmark.spot.{Spot, SpotType}

object DataSet {
  def createDataset(n: Int): Seq[Nobid] = Seq.fill(n) {
    Nobid(
      adnwId = ThreadLocalRandom.current().nextInt(0, 8),
      appName = "sampleApp",
      auctionId = UUID.randomUUID().toString,
      host = "prd-dsp03",
      loggedAt = "2017-06-30 09:07:37.677",
      mId = 234,
      nbr = 6260,
      page = "http://diamond.jp/articles/a/15434",
      resTime = 4,
      spot = Some(Spot(
        id = 2406,
        `type` = SpotType.S
      )),
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
