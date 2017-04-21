package guestbook

import scalaz.concurrent.Task
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server.blaze._
import org.http4s.server.{Server, ServerApp}


// http://http4s.org/v0.15/service/
object Boot extends ServerApp {

  import guestbook.database.GuestCommentDAO

  override def server(args: List[String]): Task[Server] = {

    import GuestBookService._

    var host: String = "localhost"
    var port: Int = 8000

    if (args.length > 3) {
      System.err.println(s"Usage: ${args.head} [host [port]]")
      System.exit(0)
    }
    
    if (args.length == 2) host = args(1)
    else if (args.length == 3) {
      host = args(1)
      port = args(2).toInt
    }
    
    // Create the database connection if it is not created
    GuestCommentDAO.create

    BlazeBuilder
      .bindHttp(port, host)
      .mountService(guestbookService, "/")
      //.mountService(staticService, "/static")
      .start

  }
}


object GuestBookService {

  import scala.concurrent.ExecutionContext.Implicits.global
  
  import guestbook.database._
  import guestbook.model._

  private object IDParam extends QueryParamDecoderMatcher[Int]("id")
  private object OffsetParam extends QueryParamDecoderMatcher[Int]("offset")
  private object LimitParam extends QueryParamDecoderMatcher[Int]("limit")

  // http://http4s.org/v0.15/static/
  /*private def static(file: String, request: Request) = 
    StaticFile.fromResource(file, Some(request)).map(Task.now).getOrElse(NotFound())*/
  
  // Serializing functions
  private def seq2GC2Json[A](seq: Seq[A])(f: (A) => GuestComment) = seq.map(f(_)).asJson
  private def toGCJson(seq: Seq[(Int, String, String)]) = seq2GC2Json(seq)(GuestComment(_))
  //private def toGCJson(seq: Seq[(Int, String, String)]) = seq2GC2Json(seq)(tuple => GuestComment(tuple._1, tuple._2, tuple._3))
  
  val guestbookService = HttpService {

    case GET -> Root / "comments" :? OffsetParam(offset) +& LimitParam(limit) => 
      Ok(GuestCommentDAO.get(offset = offset, limit = limit).map(toGCJson))

    case GET -> Root / "comments" :? OffsetParam(offset) =>
      Ok(GuestCommentDAO.get(offset = offset).map(toGCJson))

    case GET -> Root / "comments" :? LimitParam(limit) =>
      Ok(GuestCommentDAO.get(limit = limit).map(toGCJson))

    case GET -> Root / "comments" :? IDParam(id) =>
      Ok(GuestCommentDAO.get(id = id).map(toGCJson))

    case GET -> Root / "comments" =>
      Ok(GuestCommentDAO.get().map(toGCJson))

    case request @ POST -> Root =>
      request.as(jsonOf[Map[String, String]]).map(m => GuestCommentDAO.insert(m("guest"), m("comment"))).run
      NoContent()

    // http://http4s.org/v0.15/static/
    /*case request @ GET -> Root =>
      static("/templates/index.html", request)*/

  }
  
  /*val staticService = HttpService {
    
    // Serve static files
    case request @ GET -> _ if List(".js", ".css", ".map", ".html", ".webm").exists(request.pathInfo.endsWith) =>
      static(request.pathInfo, request)
      
  }*/
}