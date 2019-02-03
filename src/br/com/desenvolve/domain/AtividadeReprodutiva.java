package br.com.desenvolve.domain;

import java.util.Date;

public class AtividadeReprodutiva {
	
	private Long codigo;
	private Bovino bovino;
	private Date dataAtividade;
	private Integer idade;
	private String descricao;
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Bovino getBovino() {
		return bovino;
	}
	public void setBovino(Bovino bovino) {
		this.bovino = bovino;
	}
	public Date getDataAtividade() {
		return dataAtividade;
	}
	public void setDataAtividade(Date dataAtividade) {
		this.dataAtividade = dataAtividade;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	

}
