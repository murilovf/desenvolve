package br.com.desenvolve.domain;


public class Alimento {

	private Long codigo;	
	private String descricao;
	private String categoria;
	private Double ms;
	private Double pb;
	private Double ee;
	private Double mm;
	private Double fnd;
	private Double fda;
	private Double ndt;
	private Double preco;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Double getMs() {
		return ms;
	}
	public void setMs(Double ms) {
		this.ms = ms;
	}
	public Double getPb() {
		return pb;
	}
	public void setPb(Double pb) {
		this.pb = pb;
	}
	public Double getEe() {
		return ee;
	}
	public void setEe(Double ee) {
		this.ee = ee;
	}
	public Double getMm() {
		return mm;
	}
	public void setMm(Double mm) {
		this.mm = mm;
	}
	public Double getFnd() {
		return fnd;
	}
	public void setFnd(Double fnd) {
		this.fnd = fnd;
	}
	public Double getFda() {
		return fda;
	}
	public void setFda(Double fda) {
		this.fda = fda;
	}
	public Double getNdt() {
		return ndt;
	}
	public void setNdt(Double ndt) {
		this.ndt = ndt;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	@Override
	public String toString() {
		return "Alimento [codigo=" + codigo + ", descricao=" + descricao + ", categoria=" + categoria + ", ms=" + ms
				+ ", pb=" + pb + ", ee=" + ee + ", mm=" + mm + ", fnd=" + fnd + ", fda=" + fda + ", ndt=" + ndt
				+ ", preco=" + preco + "]";
	}
	

	
	
	

	
	
	
	
	
}
