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

@Entity (name="Usuario")
public class Usuario {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private String nome;
	@Column
	private String email;
	@Column
	private String senha;
	@Column
	private String imagem;
	@Column
	private String sobreMim;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
	private List<DicaOQueVocePrecisaSaber> dicas;
	
	public Usuario() {};
	
	public Usuario(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getSobreMim() {
		return sobreMim;
	}

	public void setSobreMim(String sobreMim) {
		this.sobreMim = sobreMim;
	}

	public long getId() {
		return id;
	}

	public List<DicaOQueVocePrecisaSaber> getDicas() {
		return dicas;
	}

	public void addDica(DicaOQueVocePrecisaSaber dica) {
		this.dicas.add(dica);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Usuario)) {
			return false;
		}
		Usuario temp = (Usuario) obj;
		return this.email.equals(temp.getEmail()) && this.id == temp.getId();
	}

	@Override
	public String toString() {
		return nome + " (" + id + ") <" + email + "> ";
	}

}
