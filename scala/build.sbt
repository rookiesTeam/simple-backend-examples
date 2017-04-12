name                := "Guestbook"

version             := "1.0"

scalaVersion        := "2.11.8"

libraryDependencies ++= Seq("org.http4s" %% "http4s-dsl" % "0.15.7a",
                            "org.http4s" %% "http4s-circe" % "0.15.7a",
                            "org.http4s" %% "http4s-blaze-client" % "0.15.7a",
                            "org.http4s" %% "http4s-blaze-server" % "0.15.7a")
