package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Dica {
	
	public Dica(){}
	
	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	//public abstract List<Concordancia> getConcordancias();

	//public abstract void addConcordancia(Concordancia concordancia);
	
	public abstract void setInapropriado();
	
	public abstract boolean isInapropriado();
	
	// public abstract boolean isFechada();
	
	public Long getId() {
		return id;
	}

}
