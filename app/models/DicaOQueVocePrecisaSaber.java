package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity (name="DicaOQueVocePrecisaSaber")
public class DicaOQueVocePrecisaSaber extends Dica {
	
	@Column
	private String nome;
	@Column
	private String descricao;
	@Column
	private int inapropriado;

	@ManyToOne(cascade=CascadeType.ALL)
	private Tema tema;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Usuario usuario;
	
	@Column
	private final int MAX_INAPROPRIADO = 3;
	@Column
	private final int MAX_CONCORDANCIAS_OU_DISCORDANCIAS = 20;
	
	public DicaOQueVocePrecisaSaber(){}
	
	public DicaOQueVocePrecisaSaber(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
		this.inapropriado = 0;
	}
	
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setInapropriado() {
		inapropriado++;
	}
	
	public boolean isInapropriado() {
		if(inapropriado >= MAX_INAPROPRIADO) {
			return true;
		}
		return false;
	}

}

