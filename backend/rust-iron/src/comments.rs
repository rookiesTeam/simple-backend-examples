use std::string::String;
use iron::prelude::*;
use iron::status;
use params::{Params, Value};
use ijr::JsonResponse;

use sample_rust_server::*;


pub fn all(req: &mut Request) -> IronResult<Response> {

    let params = req.get_ref::<Params>().unwrap();

    let limit: i64 = match params.find(&["limit"]) {
        Some(&Value::String(ref param)) => match param.parse::<i64>(){
            Ok(l) => l,
            _ => -1
        },
        _ => -1
    };
    let offset: i64 = match params.find(&["offset"]) {
        Some(&Value::String(ref param)) => match param.parse::<i64>(){
            Ok(l) => l,
            _ => -1
        },
        _ => -1
    };

    match db::get_comments(limit, offset) {
        Ok(comments) => Ok(Response::with((status::Ok, JsonResponse::json(comments)))),
        Err(err) => Ok(Response::with((status::BadRequest, format!("{0}", err))))
    }
}

pub fn new(req: &mut Request) -> IronResult<Response> {

    let params = req.get_ref::<Params>().unwrap();
    let username: String = match params.find(&["nombre"]) {
        Some(&Value::String(ref param)) => param.to_owned(),
        _ => "".to_owned()
    };
    let content: String = match params.find(&["texto"]) {
        Some(&Value::String(ref param)) => param.to_owned(),
        _ => "".to_owned()
    };

    if username == "" || content == "" {
        return Ok(Response::with((status::BadRequest, "Comment username and content should not be emtpy!")))
    }

    match db::insert_comment(username, content) {
        Ok(comment) => Ok(Response::with((status::Ok, JsonResponse::json(comment)))),
        Err(err) => Ok(Response::with((status::BadRequest, format!("{0}", err))))
    }

}
