extern crate sample_rust_server;

extern crate iron;
extern crate router;
extern crate params;
extern crate iron_json_response as ijr;

use iron::prelude::*;
use router::Router;
use ijr::JsonResponseMiddleware;

mod comments;

fn main() {

    let mut router = Router::new();

    router.get("/comments", comments::all, "comments");
    router.post("/comments", comments::new, "commments-new");

    let mut chain = Chain::new(router);
    chain.link_after(JsonResponseMiddleware);

    Iron::new(chain).http("localhost:8000").unwrap();
    println!("On 8000");
}
