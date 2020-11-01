package nova

import io.grpc.{ManagedChannelBuilder, Channel, Status}
import io.grpc.nova.user._
import io.grpc.nova.user.ZioUser.UserServiceClient
import scala.io.Source
import scalapb.zio_grpc.ZManagedChannel
import zio.{App, ZIO, Layer, Schedule}
import zio.stream.ZStream
import zio.console._
import zio.random._
import zio.duration._

object Main extends App {
  val clientLayer: Layer[Throwable, UserServiceClient] =
    UserServiceClient.live(
      ZManagedChannel(
        ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext()
      )
    )

  val program =
    for {
      _ <- UserServiceClient.find(Empty()).zipWithIndex.foreach {
        case (u, i) => printResult(u, i)
      }
    } yield ()

  final def run(args: List[String]) =
    program
      .catchAll(failed)
      .provideCustomLayer(clientLayer)
      .exitCode

  /** Prints something like:
    *
    *   User(
    *     Some(Name(Miss,Gonca,Toraman,UnknownFieldSet(Map()))),
    *     female,
    *     TR,
    *     UnknownFieldSet(Map())
    *   )
    */
  private def printResult(user: User, index: Long) =
    putStrLn(s"Result #${index + 1}: $user")

  private def failed(s: Status) =
    for {
      _ <- putStrLn(s"You sure gRPC server is running? Here is your status: $s")
    } yield ()

}
