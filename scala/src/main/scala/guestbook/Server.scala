package guestbook

import scalaz.concurrent.Task

import org.http4s.server.blaze._
import org.http4s.server.{Server, ServerApp}

import org.http4s._
//import org.http4s.circe._
import org.http4s.dsl._

/*import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._*/


// http://http4s.org/v0.15/service/
object Boot extends ServerApp {

  override def server(args: List[String]): Task[Server] = {

    import GuestBookService._

    var host: String = "localhost"
    var port: Int = 8080

    if (args.length > 3) {
      System.err.println(s"Usage: ${args.head} [host [port]]")
      System.exit(0)
    }
    
    if (args.length == 2) host = args(1)
    else if (args.length == 3) {
      host = args(1)
      port = args(2).toInt
    }

    BlazeBuilder
      .bindHttp(port, host)
      .mountService(guestbookService, "/")
      .start

  }
}


object GuestBookService {

  private object FromIDParam extends QueryParamDecoderMatcher[Int]("from")

  val guestbookService = HttpService {

    case GET -> Root / "new" :? FromIDParam(id) =>
      Ok(s"From element: $id")

    case GET -> Root / "new" =>
      Ok("New elements")

    case GET -> Root =>
      Ok("Root")

    case POST -> Root =>
      Ok("POST on Root.")

  }
}