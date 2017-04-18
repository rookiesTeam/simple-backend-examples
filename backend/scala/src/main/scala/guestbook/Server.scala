package guestbook

import scalaz.concurrent.Task

import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze._
import org.http4s.server.{Server, ServerApp}

import scala.util.{Success, Failure}

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
      //.mountService(staticService, "/static")
      .start

  }
}


object GuestBookService {
  
  import scala.concurrent.ExecutionContext.Implicits.global

  private object FromIDParam extends QueryParamDecoderMatcher[Int]("from")

  // http://http4s.org/v0.15/static/
  /*def static(file: String, request: Request) = 
    StaticFile.fromResource(file, Some(request)).map(Task.now).getOrElse(NotFound())*/
  
  val guestbookService = HttpService {

    case GET -> Root / "new" :? FromIDParam(id) =>
      Ok(s"From element: $id")

    case GET -> Root / "new" =>
      Ok("New elements")
      
    // http://http4s.org/v0.15/static/
    /*case request @ GET -> Root =>
      static("/templates/index.html", request)*/
      
    case request @ GET -> Root =>
      Ok("Root.")

    case request @ POST -> Root =>
      Ok("POST on Root.")

  }
  
  /*val staticService = HttpService {
    
    // Serve static files
    case request @ GET -> _ if List(".js", ".css", ".map", ".html", ".webm").exists(request.pathInfo.endsWith) =>
      static(request.pathInfo, request)
      
  }*/
}