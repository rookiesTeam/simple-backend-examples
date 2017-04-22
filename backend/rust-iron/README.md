Sample Rust Guestbook
====

This project has the aim to be a sample rust server for a simple guestbook webpage.
I have used [Iron][iron] as Web Framework, and [Diesel][diesel]
as ORM for persistence with Postgresql.


Installation
----

### Dependencies
This Rust server has following dependencies:
- [Rust][rust]
- [Cargo][cargo]
- [Rustup][rustup]
- [Iron][iron]
- [Router][router]
- [Params][params]
- [Iron JSON Response][iron-json-response]
- [diesel][diesel]
- [Diesel-cli][diesel-cli]
- [Postgresql][psql]
- [libpq][libpq] (dependency on GNU/Linux Debian based)

### Install
First, install and configure correctly Rust by following instructions at
[Install Rust][1]. Use rust `stable` to run this server.

Install Postgresql follow instructions at [Digital Ocean tutorial][2]. Configure
also an user for the server.

Finally install Diesel-cli to help us with Diesel database management as says in
[Disel - Getting started][4]:
```
$ cargo install diesel_cli
```


Configuration
----
To configure server's deployment you should add information for Diesel.

### Configuring Diesel
Diesel needs database configuration. To add this configuration, follow the sample
shown in [Diesel - Getting started][4], by running the follow command:
```
$ echo DATABASE_URL=postgres://username:password@localhost/diesel_demo > .env
```

This will add global variable `DATABASE_URL`, which will be later read by rust
server at `src/lib.rs`, by:
```rust
let database_url = env::var("DATABASE_URL")
      .expect("DATABASE_URL must be set");
```

To configure Diesel database into Postgresql, run:
```
$ diesel setup
```

Which will connect to postgresql, creating the given database if necessary and
running existing migrations. At this moment, there is an initial migration to
create comments' table. Otherwise, you can run, revert, redo or create a migration
with:
```
$ diesel migration generate
$ diesel migration run
$ diesel migration revert
$ diesel migration redo
```

Deployment
----
After installation run server by the following command:
```
$ cargo run
```

Authors
----

- [Ismael Taboada][ismtabo]

Acknowledgments
----

- [RookiesTeam][rookies]


[rust]: https://www.rust-lang.org/es-ES/
[iron]: http://ironframework.io/
[router]: https://github.com/iron/router
[params]: https://github.com/iron/params
[iron-json-response]: https://github.com/sunng87/iron-json-response
[cargo]: https://crates.io/
[rustup]: https://www.rustup.rs/
[diesel]: http://diesel.rs/
[diesel-cli]: https://github.com/diesel-rs/diesel/tree/master/diesel_cli
[psql]: http://www.postgresql.org.es/
[libpq]: https://www.postgresql.org/docs/9.5/static/libpq.html
[1]: https://www.rust-lang.org/en-US/install.html
[2]: https://www.digitalocean.com/community/tutorials/como-instalar-y-utilizar-postgresql-en-ubuntu-16-04-es#utilizando-roles-y-bases-de-datos-postgresql
[3]: https://rocket.rs/guide/overview/#configuration-file
[4]: http://diesel.rs/guides/getting-started/
[ismtabo]: https://github.com/ismtabo
[rookies]: https://github.com/rookiesTeam
