package controllers;

import java.util.List;

import models.Usuario;
import models.dao.GenericDAO;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import play.db.jpa.Transactional;


public class Application extends Controller {

	@Transactional
	public static Result index() {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
        return ok(index.render("Portal do Leite da Disciplina SI1 - UFCG"));
    }
       
}

