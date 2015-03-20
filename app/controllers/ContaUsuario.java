package controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import models.CriptografiaMD5;
import models.Usuario;
import models.dao.GenericDAO;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import views.html.*;

public class ContaUsuario extends Controller {
	
	private static GenericDAO dao = new GenericDAO();
	private static CriptografiaMD5 md5 = new CriptografiaMD5();
	
	@Transactional
    public static Result formularioRemoverUsuario() {
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		return ok(removerUsuario.render("Remover Usuário | Portal do Leite SI1 - UFCG"));
    }
	
	@Transactional
    public static Result removerUsuario() throws NoSuchAlgorithmException {
		
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		
		DynamicForm requestData = Form.form().bindFromRequest();
		
		if(!requestData.get("senha").equals(requestData.get("senha2"))) {
			flash("fail", "Os campos 'Senha' e 'Repetir Senha' não são iguais!");
			return redirect("/usuario/remover");
		}
		
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "Id", session().get("usuarioId"));
		
		if(!md5.criptografarSenha(requestData.get("senha")).equals(usuarios.get(0).getSenha())) {
			flash("fail", "Senha inválida!");
			return redirect("/usuario/remover");
		}
		dao.remove(usuarios.get(0));
		session().clear();
		flash("success", "Usuário removido com sucesso!");
		return redirect("/login");
    }
	
	@Transactional
    public static Result formularioEditarUsuario() {
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "Id", session().get("usuarioId"));
		return ok(editarUsuario.render("Editar Usuário | Portal do Leite SI1 - UFCG", usuarios.get(0)));
    }
	
	@Transactional
    public static Result editarUsuario() throws NoSuchAlgorithmException {
		
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		
		DynamicForm requestData = Form.form().bindFromRequest();
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "Id", session().get("usuarioId"));
		
		usuarios.get(0).setNome(requestData.get("nome"));
		usuarios.get(0).setSobreMim(requestData.get("sobre"));
		session("usuarioNome", requestData.get("nome"));
			
		dao.persist(usuarios.get(0));
		flash("success", "Usuário atualizado com sucesso!");
		return redirect("/usuario/editar");
		
	}
	
	@Transactional
    public static Result formularioAlterarSenhaUsuario() {
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		return ok(alterarSenhaUsuario.render("Alterar Senha | Portal do Leite SI1 - UFCG"));
    }
	
	@Transactional
    public static Result alterarSenhaUsuario() throws NoSuchAlgorithmException {
		
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		
		DynamicForm requestData = Form.form().bindFromRequest();
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "Id", session().get("usuarioId"));
		
		if(!md5.criptografarSenha(requestData.get("senhaAtual")).equals(usuarios.get(0).getSenha())) {
			flash("fail", "Senha atual inválida!");
			return redirect("/usuario/alterar-senha");
		}
		
		if(!requestData.get("senha").equals(requestData.get("senha2"))) {
			flash("fail", "Os campos 'Senha' e 'Repetir Senha' não são iguais!");
			return redirect("/usuario/alterar-senha");
		}
		
		if(!md5.criptografarSenha(requestData.get("senha")).equals(usuarios.get(0).getSenha())) {
			flash("fail", "Escolha uma senha diferente da atual!");
			return redirect("/usuario/alterar-senha");
		}
		
		String senhaCriptografada = md5.criptografarSenha(requestData.get("senha"));
		usuarios.get(0).setSenha(senhaCriptografada);
		dao.persist(usuarios.get(0));
		flash("success", "Senha Alterada com sucesso!");
		return redirect("/usuario/alterar-senha");
	}
	
	@Transactional
    public static Result mostrarPerfilUsuario(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/");
		}
		
		Usuario usuario = dao.findByEntityId(Usuario.class, id);
		return ok(perfilUsuario.render(usuario.getNome() + " | Portal do Leite SI1 - UFCG", usuario));
    }

}
