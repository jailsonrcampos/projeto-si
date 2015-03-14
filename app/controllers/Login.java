package controllers;

import static play.data.Form.form;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import models.CriptografiaMD5;
import models.Usuario;
import models.dao.GenericDAO;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Login extends Controller {
	
	private static GenericDAO dao = new GenericDAO();
	private static CriptografiaMD5 md5 = new CriptografiaMD5();
	static Form<Usuario> loginForm = form(Usuario.class).bindFromRequest();

	@Transactional
    public static Result mostrarFormulario() {
		if (session().get("usuario") != null) {
			return redirect("/");
		}
        return ok(login.render(loginForm, null));
    }
	
	@Transactional
	public static Result logout() {
		session().clear();
		return redirect("/login");
	}
    
	@Transactional
	public static Result autenticar() throws NoSuchAlgorithmException {
		
		Usuario usuarioAutenticar = loginForm.bindFromRequest().get();
		Usuario usuarioAutenticado = validarUsuario(usuarioAutenticar);

        if (loginForm.hasErrors() || usuarioAutenticado == null) {
        	flash("fail", "Email ou Senha Inválidos");
        	return ok(login.render(loginForm, null));
        } else {
            session().clear();
            session("usuario", usuarioAutenticado.getEmail());
            return redirect("/");
        }
    }
	
	private static Usuario validarUsuario(Usuario usuarioAutenticar) throws NoSuchAlgorithmException {
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "email", usuarioAutenticar.getEmail());
		if (usuarios == null || usuarios.isEmpty()) {
			return null;
		}
		
		if (!usuarios.get(0).getSenha().equals(md5.criptografarSenha(usuarioAutenticar.getSenha()))) {
			return null;
		}
		return usuarios.get(0);
	}
	
}