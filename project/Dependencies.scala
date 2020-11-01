import sbt._
import scalapb.compiler.Version.scalapbVersion

object Deps {
  val vScala = "2.13.3"
  val vGrpc = "1.33.1"
  val vZio = "1.0.3"
  val vScalapb = scalapbVersion
  val vScalapbJson4s = "0.10.1"

  lazy val zio = "dev.zio" %% "zio" % vZio
  lazy val zioStreams = "dev.zio" %% "zio-streams" % vZio
  lazy val grpc = "io.grpc" % "grpc-netty" % vGrpc
  lazy val scalapb = "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % vScalapb
  lazy val scalapbJson4s =
    "com.thesamet.scalapb" %% "scalapb-json4s" % vScalapbJson4s

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2"
}
