#![feature(plugin, custom_derive)]
#![plugin(rocket_codegen)]

extern crate rocket;
extern crate serde_json;
extern crate tera;
#[macro_use] extern crate rocket_contrib;
extern crate sample_rust_server;

use std::path::{Path, PathBuf};
use std::string::String;
use rocket::Request;
use rocket::response::Redirect;
use rocket::response::NamedFile;
use rocket_contrib::{JSON, Value};

#[get("/")]
fn index() -> Redirect {
    Redirect::to("/comments")
}

#[get("/static/<file..>")]
fn files(file: PathBuf) -> Option<NamedFile> {
    NamedFile::open(Path::new("static/").join(file)).ok()
}

#[error(404)]
fn not_found(req: &Request) -> JSON<Value> {
    let error: String = format!("Path {0} does not exist", req.uri().as_str());
    JSON(json!({"status": "error", "code": 404, "reason": error}))
}

mod comments;

fn main() {
    rocket::ignite()
        .mount("/", routes![index, files])
        .mount("/comments", routes![comments::all, comments::all_params, comments::new])
        .catch(errors![not_found])
        .launch();
}
