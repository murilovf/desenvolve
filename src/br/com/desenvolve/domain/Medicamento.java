package br.com.desenvolve.domain;

import java.util.Date;


public class Medicamento {

	private Long codigo;
	private Bovino bovino;
	private String nome;
	private Double peso;
	private Double dosagem;
	private Date datainicio;
	private Date datafinal;
	private Date hora;
	private String tipo;
	private String diagnostico;
	private String observacao;

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public Double getDosagem() {
		return dosagem;
	}

	public void setDosagem(Double dosagem) {
		this.dosagem = dosagem;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatafinal() {
		return datafinal;
	}

	public void setDatafinal(Date datafinal) {
		this.datafinal = datafinal;
	}
	

}
