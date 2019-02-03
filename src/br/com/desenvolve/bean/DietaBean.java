package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.desenvolve.dao.AlimentoDAO;
import br.com.desenvolve.dao.DietaDAO;
import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.domain.Dieta;
import br.com.desenvolve.domain.DietaItem;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBDieta")
@ViewScoped
public class DietaBean {
	
	private ArrayList<Alimento> itensAlimentos;
	private ArrayList<Alimento> itensFiltradosAlimentos;
	private ArrayList<DietaItem> itensSelecionados;
	private Alimento alimento;
	private Double quantidade;
	private Date horaAlimento;
	private DietaItem dietaItem;
	private Dieta dieta;
	private Double valorTotalDieta = 0.0;

	public DietaItem getDietaItem() {
		return dietaItem;
	}
	public void setDietaItem(DietaItem dietaItem) {
		this.dietaItem = dietaItem;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public ArrayList<Alimento> getItensAlimentos() {
		return itensAlimentos;
	}
	public void setItensAlimentos(ArrayList<Alimento> itensAlimentos) {
		this.itensAlimentos = itensAlimentos;
	}
	public ArrayList<Alimento> getItensFiltradosAlimentos() {
		return itensFiltradosAlimentos;
	}
	public void setItensFiltradosAlimentos(ArrayList<Alimento> itensFiltradosAlimentos) {
		this.itensFiltradosAlimentos = itensFiltradosAlimentos;
	}
	public Alimento getAlimento() {
		return alimento;
	}
	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}		
	public ArrayList<DietaItem> getItensSelecionados() {
		return itensSelecionados;
	}
	public void setItensSelecionados(ArrayList<DietaItem> itensSelecionados) {
		this.itensSelecionados = itensSelecionados;
	}
	public void setHoraAlimento(Date horaAlimento) {
		this.horaAlimento = horaAlimento;
	}	
	public Date getHoraAlimento() {
		return horaAlimento;
	}
	public Dieta getDieta() {
		return dieta;
	}
	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}
	public void setValorTotalDieta(Double valorTotalDieta) {
		this.valorTotalDieta = valorTotalDieta;
	}
	public Double getValorTotalDieta() {
		return valorTotalDieta;
	}
	
	@PostConstruct
	public void prepararPesquisa(){
		dieta = new Dieta();
		alimento = new Alimento();
		quantidade=0.0;
		dietaItem = new DietaItem();
		itensSelecionados = new ArrayList<>();

		try {
			AlimentoDAO dao = new AlimentoDAO();		
			itensAlimentos = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	public void adiciona(){
		boolean validaCampos = true;
		
		if (quantidade == null){
			JSFUtil.adicionarMensagemErro("Quantidade deve ser informada");
			validaCampos = false;
		}
		
		if(horaAlimento == null){
			JSFUtil.adicionarMensagemErro("Hora do alimento deve ser informada");
			validaCampos = false;
		}
		
		if(validaCampos){
			dietaItem.setAlimento(alimento);
			dietaItem.setQuantidade(quantidade);	
			dietaItem.setHora(horaAlimento);
			dietaItem.setValor(alimento.getPreco() * quantidade);
			itensSelecionados.add(dietaItem);
			valorTotalDieta += dietaItem.getValor();
			dietaItem = new DietaItem();
			alimento = new Alimento();
			quantidade=0.0;
			horaAlimento = null;
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
		
	}
	
	public void excluir(){
		for (int i = 0; i < itensSelecionados.size(); i++) {
			if(dietaItem.getAlimento().getCodigo()==itensSelecionados.get(i).getAlimento().getCodigo()){
				itensSelecionados.remove(i);
			}
		}
		valorTotalDieta -= dietaItem.getValor();
		dietaItem = new DietaItem();
		alimento = new Alimento();
		quantidade=0.0;
	}
	
	public void editarAlimentoDieta(){
		boolean validaCampos = true;

		if (dietaItem.getQuantidade() == null){
			JSFUtil.adicionarMensagemErro("Quantidade deve ser informada");
			validaCampos = false;
		}

		if(dietaItem.getHora() == null){
			JSFUtil.adicionarMensagemErro("Hora do alimento deve ser informada");
			validaCampos = false;
		}

		if(validaCampos){
			valorTotalDieta = 0.0;
			for (int i = 0; i < itensSelecionados.size(); i++) {
				if(dietaItem.getAlimento().getCodigo()==itensSelecionados.get(i).getAlimento().getCodigo()){
					itensSelecionados.get(i).setQuantidade(dietaItem.getQuantidade());
					itensSelecionados.get(i).setValor(itensSelecionados.get(i).getAlimento().getPreco() * dietaItem.getQuantidade());
					itensSelecionados.get(i).setHora(dietaItem.getHora());
				}
				valorTotalDieta += itensSelecionados.get(i).getValor();
			}
			dietaItem = new DietaItem();
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
		
	}
	
	public void novo(){
		try {
				DietaDAO dao = new DietaDAO();
				dao.salvarPraTodos(dieta, itensSelecionados, valorTotalDieta);
				JSFUtil.adicionarMensagemSucesso("Dieta Salva com Sucesso");
				dieta = new Dieta();
				valorTotalDieta = 0.0;
				itensSelecionados.clear();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}	
	
	public void validarCamposItem(){
		boolean validaCampos = true;
		
		if(itensSelecionados.size()<=0){
			JSFUtil.adicionarMensagemErro("Deverá informar pelo menos um item para a dieta.");
			validaCampos = false;
		}
		
		if(dieta.getNome().equals("") || dieta.getNome() == null){
			JSFUtil.adicionarMensagemErro("Nome da dieta deverá ser informado.");
			validaCampos = false;
		}
		
		if (validaCampos){
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
			
			
	}

}
