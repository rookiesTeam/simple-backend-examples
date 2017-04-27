Sample Bottle Guestbook
====

This project has the aim to be a sample bottle server for a simple guestbook webpage.
I have used [Bottle](https://bottlepy.org/docs/dev/) as Web Framework, and [MongoDB](https://www.mongodb.com/es)for persistence.


Installation
----

### Dependencies
This Bottle server has following dependencies:
- [Bottle][bottle]
- [MongoPlugin][mongoplugin]

### Install
First, install and configure correctly Python and MongoDB

Second, start mongo
```
$ mongod -dbpath . 
```

Finally install dependencies
```
$ pip3 install -r requirements.txt
```

Deployment
----
After installation run server by the following command:
```
$ python3 server.py
```

[bottle]: https://bottlepy.org/docs/dev/
[mongoplugin]: https://pypi.python.org/pypi/bottle-mongo/