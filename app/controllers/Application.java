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
	
	private static GenericDAO dao = new GenericDAO();

	@Transactional
	public static Result index() {
		if (session().get("usuario") == null) {
			return redirect(routes.Login.mostrarFormulario());
		}
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "email", session().get("usuario"));
        return ok(index.render("Portal do Leite da Disciplina SI1 - UFCG", usuarios.get(0)));
    }
       
}

