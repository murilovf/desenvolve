package br.com.desenvolve.domain;


public class AlertaVl {
	
	private Long codigo;
	private Double peso;
	private Double altura;
	private Double circunferencia;
	private Integer ativo;
	
	
	public Integer getAtivo() {
		return ativo;
	}
	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Double getCircunferencia() {
		return circunferencia;
	}
	public void setCircunferencia(Double circunferencia) {
		this.circunferencia = circunferencia;
	}


	
	

}
