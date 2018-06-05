package com.github.saint1991.serialization.benchmark.msgpack.msgpack4z

import msgpack4z._
import msgpack4z.CodecInstances.all._

import com.github.saint1991.serialization.benchmark.dataset.{Nobid, Spot}

object Codec {

  private [this] final val Factory: PackerUnpackerFactory = new PackerUnpackerFactory {
    def packer: MsgOutBuffer = MsgOutBuffer.create()
    def unpacker(bytes: Array[Byte]) = MsgInBuffer(bytes)
  }

  private [this] final val Codec: CaseMapCodec[String] = CaseMapCodec.string(Factory)

  implicit val spotCodec: MsgpackCodec[Spot] = Codec.codec(Spot.apply _, Spot.unapply _)("id", "name")
  val codec: MsgpackCodec[Nobid] = Codec.codec(Nobid.apply _, Nobid.unapply _)(
    "adnwId",
    "appName",
    "auctionId",
    "host",
    "loggedAt",
    "mId",
    "nbr",
    "page",
    "resTime",
    "spot",
    "history",
    "tags"
  )

}
