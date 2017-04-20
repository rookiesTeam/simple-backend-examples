Sample Rust Guestbook
====

This project has the aim to be a sample rust server for a simple guestbook webpage.
I have used [Rocket](https://rocket.rs/) as Web Framework, and [Diesel](http://diesel.rs/)
as ORM for persistence with Postgresql.


Installation
----

### Dependencies
This Rust server has following dependencies:
- [Rust][rust]
- [Cargo][cargo]
- [Rustup][rustup]
- [Rocket][rocket]
- [diesel][diesel]
- [Diesel-cli][diesel-cli]
- [Postgresql][psql]
- [libpq][libpq] (dependency on GNU/Linux Debian based)

### Install
First, install and configure correctly Rust by following instructions at
[Install Rust][1]. You should install `nightly-2017-04-15` for correct deploy
of Rocket.

To change your default toolchain of Rust you should run commands below:
```
$ rustup install nightly-2017-04-15
$ rustup default nightly-2017-04-15
```

Or you could later run the server with:
```
$ rustup run nightly-2017-04-15 cargo build
```

Install Postgresql follow instructions at [Digital Ocean tutorial][2]. Configure
also an user for the server.

Finally install Diesel-cli to help us with Diesel database management as says in
[Disel - Getting started][4]:
```
$ cargo install diesel_cli
```


Configuration
----
To configure server's deployment you should add information for Rocket and
Diesel.

### Configuring Rocket
As it is showed in [Rocket - Configuration file][3], you can add personal configuration
for each enviroment (development, staging, production) or global, by adding information
at file `Rocket.toml` as the following sample:
```toml
[development]
address = "localhost"
port = 8000
workers = max(number_of_cpus, 2)
log = "normal"

[staging]
address = "0.0.0.0"
port = 80
workers = max(number_of_cpus, 2)
log = "normal"

[production]
address = "0.0.0.0"
port = 80
workers = max(number_of_cpus, 2)
log = "critical"
```

### Configuring Diesel
Diesel needs database configuration. To add this configuration, follow the sample
shown in [Disel - Getting started][4], by running the follow command:
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


[rust]: https://www.rust-lang.org/es-ES/
[cargo]: https://crates.io/
[rustup]: https://www.rustup.rs/
[rocket]: https://rocket.rs/
[diesel]: http://diesel.rs/
[diesel-cli]: https://github.com/diesel-rs/diesel/tree/master/diesel_cli
[psql]: http://www.postgresql.org.es/
[libpq]: https://www.postgresql.org/docs/9.5/static/libpq.html
[1]: https://www.rust-lang.org/en-US/install.html
[2]: https://www.digitalocean.com/community/tutorials/como-instalar-y-utilizar-postgresql-en-ubuntu-16-04-es#utilizando-roles-y-bases-de-datos-postgresql
[3]: https://rocket.rs/guide/overview/#configuration-file
[4]: http://diesel.rs/guides/getting-started/
