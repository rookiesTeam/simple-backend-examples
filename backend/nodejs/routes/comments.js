var express = require('express');
var router = express.Router();
var guestbookdb = require('../models/guestbookdatabase.js');
guestbookdb.testTable();

router.route('/')
	.get(function(req, res, next) {
		guestbookdb.getComments(function(error, data) {
			res.json(data);
		});
	})
	.post(function(req, res, next) {
		guestbookdb.insertComment({nombre: req.body.nombre, texto: req.body.texto});
		res.end();
	});


router.route('/:id')
	.get(function(req, res, next) {
		guestbookdb.getComment(req.params.id, function(error, comment) {
			if (error)
				res.json({error: error});
			else
				res.json(comment);
		});
	})
	.put(function(req, res, next) {
		if (req.params.id && req.body.nombre && req.body.texto) {
			guestbookdb.updateComment({
				id: req.params.id,
				nombre: req.body.nombre,
				texto: req.body.texto
			})
		}
		res.end();
	})
	.delete(function(req, res, next) {
		guestbookdb.deleteComment(req.params.id);
		res.end();
	});

module.exports = router;
