package controllers;

import static play.data.Form.form;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.CriptografiaMD5;
import models.Usuario;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Registro extends Controller {
	
	private static GenericDAO dao = new GenericDAO();
	private static CriptografiaMD5 md5 = new CriptografiaMD5();
	static Form<Usuario> registroForm = form(Usuario.class).bindFromRequest();

	@Transactional
    public static Result mostrarFormulario() {
		if (session().get("usuarioId") != null) {
			return redirect("/");
		}
		return ok(registro.render("Registro de Usuário | Portal do Leite SI1 - UFCG", registroForm));
    }
    
	@Transactional
	public static Result registrar() throws NoSuchAlgorithmException{
		
		DynamicForm requestData = Form.form().bindFromRequest();
		Usuario usuarioRegistrar = registroForm.bindFromRequest().get();
    	
		if (registroForm.hasErrors()) {
			flash("fail", "Erro no preenchimento do formulário! Verifique os dados digitados e tente novamente.");
			return redirect("/registro");
        } 
		
		if (!validarEmail(usuarioRegistrar.getEmail())) {
			flash("fail", "O email digitado não é um email válido!");
			return redirect("/registro");
        } 
		
		if (!validarSenha(usuarioRegistrar.getSenha())) {
			flash("fail", "A senha digitada não é uma senha válida!");
			return redirect("/registro");
        }
		
		if (!usuarioRegistrar.getSenha().equals(requestData.get("senha2"))) {
			flash("fail", "Os campos 'Senha' e 'Repetir Senha' não são iguais!");
			return redirect("/registro");
        }
		
		if (!validarNome(usuarioRegistrar.getNome())) {
			flash("fail", "O Nome digitado não é um nome válido!");
			return redirect("/registro");
        }
		
		if (hasEmailCadastrado(usuarioRegistrar.getEmail())) {
			flash("fail", "O email já está em uso por outro usuário!");
			return redirect("/registro");
	    }
		
		String senhaCriptografada = md5.criptografarSenha(usuarioRegistrar.getSenha());
		usuarioRegistrar.setSenha(senhaCriptografada);
        dao.persist(usuarioRegistrar);
        flash("success", "Registro realizado com sucesso! Agora você pode fazer o Login.");
        return redirect("/login");

    }
	
	private static boolean hasEmailCadastrado(String email) {
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "email", email);
		if(usuarios != null && usuarios.size() > 0) { 
			return true;
		}
		return false;
	}
	
	private static boolean validarEmail(String email) {
	    Pattern padrao = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
	    Matcher equiparador = padrao.matcher(email);
	    if (equiparador.find()) {
	    	return true;
	    }
	    return false;
	}
	
	private static boolean validarSenha(String senha) {
		if(senha.length() < 6) {
			return false;
		}
		return true;
	}
	
	private static boolean validarNome(String nome) {
		if(nome.length() < 3) {
			return false;
		}
		return true;
	}

}