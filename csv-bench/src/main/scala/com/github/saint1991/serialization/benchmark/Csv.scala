package com.github.saint1991.serialization.benchmark

import com.github.saint1991.serialization.benchmark.dataset._

object Csv {

  def toCsv(nobid: Nobid): String = {
    val tags = nobid.tags.map(entry => s"${entry._1}#${entry._2}")
    s"${nobid.adnwId},${nobid.appName},${nobid.auctionId},${nobid.host},${nobid.loggedAt},${nobid.mId},${nobid.nbr},${nobid.page},${nobid.resTime},${nobid.spot.id}_${nobid.spot.`type`},${nobid.history.mkString("_")},${tags.mkString("_")}"
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
      spot = Spot (
        id = spot(0).toInt,
        `type` = SpotType.withName(spot(1))
      ),
      history = line(10).split("_"),
      tags = tags
    )
  }

}
