enablePlugins(ScalaNativePlugin)

scalaVersion := "2.11.12"

version := {
  import sys.process._
  ("git describe --always --dirty=-SNAPSHOT --match v[0-9].*" !!).tail.trim
}

nativeMode := {
  if (version.value.endsWith("SNAPSHOT")) "debug" else "release"
}

sourceGenerators in Compile += Def.task {
  val file = (sourceManaged in Compile).value / "BuildInfo.scala"
  IO.write(
    file,
    s"""|package codenames
        |object BuildInfo {
        |  val version: String = "${version.value}"
        |}""".stripMargin
  )
  Seq(file)
}.taskValue
