name                := "Guestbook"

version             := "1.0"

scalaVersion        := "2.11.8"

scalacOptions       += "-deprecation"

libraryDependencies ++= Seq("org.http4s" %% "http4s-dsl" % "0.15.8",
                            "org.http4s" %% "http4s-circe" % "0.15.8",
                            "org.http4s" %% "http4s-blaze-client" % "0.15.8",
                            "org.http4s" %% "http4s-blaze-server" % "0.15.8",
                            "io.circe" %% "circe-core" % "0.7.0",
                            "io.circe" %% "circe-generic" % "0.7.0",
                            "io.circe" %% "circe-literal" % "0.7.0",
                            "org.xerial" % "sqlite-jdbc" % "3.7.15-M1",
                            "com.typesafe.slick" %% "slick" % "3.2.0",
                            "org.slf4j" % "slf4j-nop" % "1.6.4",
                            "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0")
