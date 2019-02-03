package br.com.desenvolve.domain;

import java.util.Date;

public class Dieta {
	
	private Long codigo;
	private Bovino bovino;
	private String nome;
	private Double valorTotal;
	private Date dataInicial;
	private Date dataFinal;
	
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
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Date getDataFinal() {
		return dataFinal;
	}	
	@Override
	public String toString() {
		return "Dieta [codigo=" + codigo + ", bovino=" + bovino + ", nome=" + nome + ", valorTotal=" + valorTotal + "]";
	}
	
	
	

}
