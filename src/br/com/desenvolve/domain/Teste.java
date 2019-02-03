package br.com.desenvolve.domain;

import org.primefaces.model.UploadedFile;

public class Teste {
	
	private Long codigo;	
	private String nome;
	private UploadedFile file;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "Teste [codigo=" + codigo + ", nome=" + nome + "]";
	}
	
	
	
}
