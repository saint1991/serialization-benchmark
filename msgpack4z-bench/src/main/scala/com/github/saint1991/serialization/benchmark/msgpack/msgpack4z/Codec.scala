package com.github.saint1991.serialization.benchmark.msgpack.msgpack4z

import msgpack4z.CodecInstances.all._
import msgpack4z.{CaseCodec, MsgpackCodec}

import com.github.saint1991.serialization.benchmark.dataset.{Nobid, Spot}

object Codec {
  implicit val spotCodec: MsgpackCodec[Spot] = CaseCodec.codec(Spot.apply _, Spot.unapply _)
  val codec: MsgpackCodec[Nobid] = CaseCodec.codec(Nobid.apply _, Nobid.unapply _)
}
