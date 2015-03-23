package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Dica;
import models.DicaOQueVocePrecisaSaber;
import models.Dificuldade;
import models.Tema;
import models.Usuario;
import models.dao.GenericDAO;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import play.db.jpa.Transactional;


public class Application extends Controller {
	
	private static final GenericDAO dao = new GenericDAO();

	@Transactional
	public static Result index() {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		List<Tema> temas = dao.findAllByClass(Tema.class);
		List<DicaOQueVocePrecisaSaber> dicas = dao.findAllByClass(DicaOQueVocePrecisaSaber.class);
        return ok(index.render("Portal do Leite da Disciplina SI1 - UFCG", temas, dicas));
    }
	
	@Transactional
	public static Result mostrarDicas(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		List<Tema> temas = dao.findAllByClass(Tema.class);
		Tema temaAtual = dao.findByEntityId(Tema.class, id);
        return ok(dicas.render("Dicas para o tema: " +temaAtual.getNome() + " | Portal do Leite SI1 - UFCG", temas, temaAtual));
    }
	
	@Transactional
	public static Result mostrarDica(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		List<Tema> temas = dao.findAllByClass(Tema.class);
		DicaOQueVocePrecisaSaber dicaAtual = dao.findByEntityId(DicaOQueVocePrecisaSaber.class, id);
        return ok(dica.render( dicaAtual.getNome() + " | Portal do Leite SI1 - UFCG", temas, dicaAtual));
    }
	
	@Transactional
	public static Result formularioAdicionarDica(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		List<Tema> temas = dao.findAllByClass(Tema.class);
		Tema temaAtual = dao.findByEntityId(Tema.class, id);
        return ok(adicionarDica.render("Adicionar Nova Dica | Portal do Leite SI1 - UFCG", temas, temaAtual));
    }
	
	@Transactional
	public static Result adicionarDica(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		DynamicForm requestData = Form.form().bindFromRequest();
		DicaOQueVocePrecisaSaber dica = new DicaOQueVocePrecisaSaber(requestData.get("nome"),requestData.get("descricao"));
		Tema temaAtual = dao.findByEntityId(Tema.class, id);
		Usuario usuario = dao.findByEntityId(Usuario.class, Long.parseLong(session().get("usuarioId")));
		temaAtual.addDica(dica);
		dica.setTema(temaAtual);
		dica.setUsuario(usuario);
		
		dao.persist(temaAtual);
		dao.persist(dica);
		dao.persist(usuario);
		
        return redirect("/");
    }
	
	@Transactional
	public static Result formularioAdicionarDificuldade(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		List<Tema> temas = dao.findAllByClass(Tema.class);
		Tema temaAtual = dao.findByEntityId(Tema.class, id);
		Usuario usuario = dao.findByEntityId(Usuario.class, Long.parseLong(session().get("usuarioId")));
		if(!temaAtual.hasDificuldade(usuario)) {
			return ok(adicionarDificuldade.render("Avaliar Dificuldade | Portal do Leite SI1 - UFCG", temas, temaAtual));
		}
		flash("fail", "Você já avaliou a dificuldade para o tema!");
    	return redirect("/dicas/" + id);
    }
      
	@Transactional
	public static Result adicionarDificuldade(Long id) {
		if (session().get("usuarioId") == null) {
			return redirect("/login");
		}
		DynamicForm requestData = Form.form().bindFromRequest();
		Tema temaAtual = dao.findByEntityId(Tema.class, id);
		Usuario usuario = dao.findByEntityId(Usuario.class, Long.parseLong(session().get("usuarioId")));
		if(!temaAtual.hasDificuldade(usuario)) {
			Dificuldade dificuldade = new Dificuldade(temaAtual, usuario.getId(), Double.parseDouble((requestData.get("dificuldade"))));
			temaAtual.addDificuldade(dificuldade);
			dificuldade.setTema(temaAtual);
			dao.persist(temaAtual);
			dao.persist(dificuldade);
			flash("success", "Avaliação adicionada com sucesso!");
	    	return redirect("/dicas/" + id);
		}
		flash("fail", "Você já avaliou a dificuldade para o tema!");
    	return redirect("/dicas/" + id);
    }
}

