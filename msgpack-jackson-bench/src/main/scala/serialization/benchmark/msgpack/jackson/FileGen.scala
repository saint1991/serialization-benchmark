package serialization.benchmark.msgpack.jackson

import java.io._

import scala.util.control.Exception._

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.msgpack.jackson.dataformat.MessagePackFactory

import com.github.saint1991.serialization.benchmark.BenchmarkSettings.DatasetSize
import com.github.saint1991.serialization.benchmark.dataset.{DataSet, Nobid}
import com.github.saint1991.serialization.benchmark.FileUtil
import com.github.saint1991.serialization.benchmark.FileUtil.NewLineBytes

object FileGen extends App {

  val dataset = DataSet.createDataset(DatasetSize)

  val mapper = new ObjectMapper(new MessagePackFactory())
  mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
  mapper.registerModule(DefaultScalaModule)

  final val outFile = FileUtil.mkOutFile("nobid-jackson.msgpack")
  val out = new BufferedOutputStream(new FileOutputStream(outFile.toJava))

  // write to file
  allCatch andFinally {
    out.flush()
    out.close()
  } apply writeToFile(dataset, mapper, out)

  private def writeToFile(dataset: Seq[Nobid], mapper: ObjectMapper, file: OutputStream): Unit =
    dataset.foreach { r =>
      mapper.writeValue(file, r)
      file.write(NewLineBytes)
    }
}
