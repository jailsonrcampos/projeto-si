import java.util.List;
import models.Tema;
import play.*;
import play.db.jpa.JPA;
import models.dao.GenericDAO;

public class Global extends GlobalSettings {
	
	private static final GenericDAO dao = new GenericDAO();
	
	@Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			
			public void invoke() throws Throwable {
				
				List<Tema> temas = dao.findAllByClass(Tema.class);
				if(temas.size() == 0) {
					
					dao.persist(new Tema("Análise x Design"));
					dao.persist(new Tema("Orientação à Objeto (OO)"));
					dao.persist(new Tema("GRASP"));
					dao.persist(new Tema("GoF"));
					dao.persist(new Tema("Arquitetura"));
					dao.persist(new Tema("Play Framework"));
					dao.persist(new Tema("Javascript"));
					dao.persist(new Tema("HTML+CSS+Bootstrap"));
					dao.persist(new Tema("Heroku"));
					dao.persist(new Tema("Labs"));
					dao.persist(new Tema("Minitestes"));
					dao.persist(new Tema("Projeto"));
					
				}
				
			}
		});
	}
	
	public void onStop(Application app) {
		Logger.info("desligada...");
	}

}
