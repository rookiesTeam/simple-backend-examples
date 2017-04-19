var sqlite3 = require('sqlite3').verbose(),
db = new sqlite3.Database('./data/guestbook'),
GUESTBOOK = {};

GUESTBOOK.testTable = function() {
	db.get("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = 'comentario'", function(error, row) {
		if (row['count(*)'] == 0) {
			GUESTBOOK.createTable();
		}
	});
}
 
GUESTBOOK.createTable = function() {
	db.run("DROP TABLE IF EXISTS comentario");
	db.run("CREATE TABLE IF NOT EXISTS comentario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, texto TEXT)");
	console.log("La tabla 'comentario' ha sido correctamente creada");
}

GUESTBOOK.insertComment = function(data) {
	var stmt = db.prepare("INSERT INTO comentario VALUES (?,?,?)");
	stmt.run(null,data.nombre,data.texto);
	stmt.finalize();
}

GUESTBOOK.updateComment = function(data) {
	var stmt = db.prepare("UPDATE comentario SET nombre=?, texto=? WHERE id=?");
	stmt.run(data.nombre,data.texto, data.id);
	stmt.finalize();
}

GUESTBOOK.deleteComment = function(commentId) {
	var stmt = db.prepare("DELETE FROM comentario WHERE id=?");
	stmt.run(commentId);
	stmt.finalize();
}

GUESTBOOK.getComments = function(callback) {
	db.all("SELECT * FROM comentario", function(err, rows) {
		if(err) {
			throw err;
		}
		else {
			callback(null, rows);
		}
	});
}

GUESTBOOK.getComment = function(commentId,callback) {
	stmt = db.prepare("SELECT * FROM comentario WHERE id = ?");
    stmt.bind(commentId); 
    stmt.get(function(error, row) {
    	if(error) {
            throw err;
        }
        else {
            if(row) {
                callback("", row);
            }
            else {
            	callback("El comentario no existe", "");
            }
        }
    });
}

module.exports = GUESTBOOK;