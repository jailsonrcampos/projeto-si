package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity (name="Concordancia")
public class Concordancia {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Usuario usuario;
	
	@Column
	private boolean valor;

	@ManyToOne(cascade=CascadeType.ALL)
	private Dica dica;
	
	@Column
	private String razao;
	
	public Concordancia() {}
	
	public Concordancia(Usuario usuario, Dica dica, boolean valor) {
		this.usuario = usuario;
		this.dica = dica;
		this.valor = valor;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public Long getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean getValor() {
		return valor;
	}

	public void setValor(boolean valor) {
		this.valor = valor;
	}

	public Dica getDica() {
		return dica;
	}

	public void setDica(Dica dica) {
		this.dica = dica;
	}
	
}
