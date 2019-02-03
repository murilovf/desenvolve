package br.com.desenvolve.domain;

import java.util.Date;

public class Vacina {

	private Long codigo;
	private Bovino bovino;
	private String nome;
	private String finalidade;
	private Integer situacao;
	private Date datavacina;
	private Date dataaplicacao;
	
	
	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}
	public Date getDataaplicacao() {
		return dataaplicacao;
	}
	public void setDataaplicacao(Date dataaplicacao) {
		this.dataaplicacao = dataaplicacao;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getSituacao() {
		return situacao;
	}
	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
	public Date getDatavacina() {
		return datavacina;
	}
	public void setDatavacina(Date datavacina) {
		this.datavacina = datavacina;
	}
	@Override
	public String toString() {
		return "Vacina [codigo=" + codigo + ", bovino=" + bovino + ", nome=" + nome + ", situacao=" + situacao
				+ ", datavacina=" + datavacina + "]";
	}
	
	
	
	
}
