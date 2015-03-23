package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity (name="Dificuldade")
public class Dificuldade implements Comparable<Dificuldade>{
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Tema tema;
	
	@Column
	private Long idUsuario;
	
	@Column
	private Double valor;
	
	public Dificuldade(){}
	
	public Dificuldade(Tema tema, Long idUsuario, Double valor) {
		this.tema = tema;
		this.idUsuario = idUsuario;
		this.valor = valor;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public long getId() {
		return id;
	}

	@Override
	public int compareTo(Dificuldade dificuldade) {
		return Integer.parseInt((valor - dificuldade.getValor()) + "");
	}
	
}
