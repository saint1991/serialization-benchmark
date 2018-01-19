package com.github.saint1991.samples

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom


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
  spotId: Int,
  spotType: String,
  history: Seq[String],
  tags: Map[String, String]
)



object Nobid {

  def toCsv(nobid: Nobid): String ={
    val tags = nobid.tags.map(entry => s"${entry._1}#${entry._2}")
    s"${nobid.adnwId},${nobid.appName},${nobid.auctionId},${nobid.host},${nobid.loggedAt},${nobid.mId},${nobid.nbr},${nobid.page},${nobid.resTime},${nobid.spotId}_${nobid.spotType},${nobid.history.mkString("_")},${tags.mkString("_")}"
  }

  def fromCsv(csv: String): Nobid = {
    val line = csv.split(",")
    val spot = line(9).split("_")
    val tags = line(11).split("_").map { i =>
      val entry = i.split("#")
      entry(0) -> entry(1)
    }.toMap

    Nobid(
      adnwId = line(0).toInt,
      appName = line(1),
      auctionId = line(2),
      host = line(3),
      loggedAt = line(4),
      mId = line(5).toInt,
      nbr = line(6).toInt,
      page = line(7),
      resTime = line(8).toInt,
      spotId = spot(0).toInt,
      spotType = spot(1),
      history = line(10).split("_"),
      tags = tags
    )
  }

}



object DataSet {
  def createDataset(n: Int): Seq[Nobid] = for {
    i <- 1 to n
    data = Nobid(
      adnwId = ThreadLocalRandom.current().nextInt(0, 8),
      appName = "sampleApp",
      auctionId = UUID.randomUUID().toString,
      host = "prd-dsp03",
      loggedAt = "2017-06-30 09:07:37.677",
      mId = 234,
      nbr = 6260,
      page = "http://diamond.jp/articles/-/15434",
      resTime = 4,
      spotId = 2406,
      spotType = "A",
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
  } yield data
}

