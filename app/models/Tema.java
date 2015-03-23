package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity (name="Tema")
public class Tema {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column
	private String nome;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
	private List<DicaOQueVocePrecisaSaber> dicas;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
	private List<Dificuldade> dificuldades;
	
	public Tema(){}
	
	public Tema(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<DicaOQueVocePrecisaSaber> getDicas() {
		return dicas;
	}
	
	public void addDica(DicaOQueVocePrecisaSaber dica) {
		this.dicas.add(dica);
	}
	
	public List<Dificuldade> getDificuldades() {
		return dificuldades;
	}

	public void addDificuldade(Dificuldade dificuldade) {
		this.dificuldades.add(dificuldade);
	}
	
	public boolean hasDificuldade(Usuario usuario) {
		if(dificuldades.size() == 0) {
			return false;
		}
		for (int i = 0; i < dificuldades.size(); i++) {
			if(dificuldades.get(i).getIdUsuario().equals(usuario.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public Double getMedianaDificuldade() {
		if(dificuldades.size() == 0) {
			return null;
		}
		if(dificuldades.size() % 2 == 0 ) {
			int metade = dificuldades.size()/2;
			return (dificuldades.get(metade-1).getValor() + dificuldades.get(metade).getValor())/2.0;
		}
		return dificuldades.get( ( ( dificuldades.size() + 1 ) / 2 ) -1 ).getValor();
	}
	
	public Double getMediaDificuldade() {
		if(dificuldades.size() == 0) {
			return null;
		}
		Double total = 0.0;
		for (int i = 0; i < dificuldades.size(); i++) {
			total += dificuldades.get(i).getValor();
		}
		return total/dificuldades.size();
	}
	
}
