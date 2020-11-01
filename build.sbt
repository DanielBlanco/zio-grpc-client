ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.novacore"
ThisBuild / organizationName := "novacore"

lazy val commonSettings = Seq(
  scalaVersion := Deps.vScala
)

lazy val root = (project in file("."))
  .settings(
    name := "grpc-client",
    update / aggregate := false,
    commonSettings,
    libraryDependencies ++= Seq(
      Deps.grpc,
      Deps.scalapb,
      Deps.scalapbJson4s,
      Deps.scalaTest % Test
    ),
    Compile / PB.targets := Seq(
      scalapb.gen(grpc = true) -> (sourceManaged in Compile).value,
      scalapb.zio_grpc.ZioCodeGenerator -> (sourceManaged in Compile).value
    )
  )

run / fork := true
