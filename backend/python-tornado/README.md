Sample Tornado Guestbook
========================

This simple project is an example of a REST server done
with Tornado framework and Peewee as ORM.

Dependencies
----
This server has the following dependecies, source `requirements.txt`:
- Python3 (>=3.5)
- Tornado
- Peewee

Deployment
----
To run this server we recommend you to create a virtual enviroment, as third parties python modules are going to be installed. Make sure you create one based on python version >= 3.5:
```
$ virtualenv -p python3.5 .venv
```

Install dependencies with project's Makefile:
```
$ source .venv/bin/activate
$ make
```

In order to execute the server, load that virtual env and following commands:
```
$ source .venv/bin/activate
$ python main.py
```


