package com.github.saint1991.serialization.benchmark.protobuf

import com.github.saint1991.serialization.benchmark.protobuf.nobid.Nobid
import com.github.saint1991.serialization.benchmark.protobuf.spot.Spot

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
      page = "http://diamond.jp/articles/a/15434",
      resTime = 4,
      spot = Some(Spot(
        id = 2406,
        name = "Mie"
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
