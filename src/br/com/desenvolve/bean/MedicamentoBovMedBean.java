package br.com.desenvolve.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.util.JSFUtil;


@ManagedBean(name = "MBMedicamentoGeralBovMed")
@ViewScoped
public class MedicamentoBovMedBean {
	
	
	
	private ArrayList<Medicamento> listaMedicamentos;
	private Medicamento medicamento;
	private String dataInicial;
	private String dataFinal;
	private ArrayList<Bovino> bovinos;
	private ArrayList<Bovino> bovinosSelecionados;
	
	

	public ArrayList<Bovino> getBovinos() {
		return bovinos;
	}

	public void setBovinos(ArrayList<Bovino> bovinos) {
		this.bovinos = bovinos;
	}

	public ArrayList<Bovino> getBovinosSelecionados() {
		return bovinosSelecionados;
	}

	public void setBovinosSelecionados(ArrayList<Bovino> bovinosSelecionados) {
		this.bovinosSelecionados = bovinosSelecionados;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public ArrayList<Medicamento> getListaMedicamentos() {
		return listaMedicamentos;
	}

	public void setListaMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}


	public void carregaPesquisa() throws SQLException{

		BovinoDAO dao = new BovinoDAO();
		bovinos = dao.listarAtivos();
		listaMedicamentos = new ArrayList<>();
		medicamento = new Medicamento();
		dataFinal=null;
		dataInicial=null;
					
	}
	
	public void seguinte() throws ParseException{
		
		if(bovinosSelecionados.size()>0){
			
			medicamento.setDatainicio(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial));
			medicamento.setDatafinal(new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal));
			
			for (int i = 0; i < bovinosSelecionados.size(); i++) {
				
				Medicamento medicamentoAux = new Medicamento();
				medicamentoAux.setBovino(bovinosSelecionados.get(i));
				medicamentoAux.setNome(medicamento.getNome());
				medicamentoAux.setDatainicio(medicamento.getDatainicio());
				medicamentoAux.setDatafinal(medicamento.getDatafinal());
				medicamentoAux.setHora(medicamento.getHora());
				medicamentoAux.setTipo(medicamento.getTipo());
				medicamentoAux.setDiagnostico(medicamento.getDiagnostico());
				medicamentoAux.setObservacao(medicamento.getObservacao());
				medicamentoAux.setPeso(0.0);
				medicamentoAux.setDosagem(0.0);
				
				listaMedicamentos.add(medicamentoAux);
				
			}
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selec", listaMedicamentos);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				ec.redirect("./medicamentoGeralMedicamento.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		}else{
			JSFUtil.adicionarMensagemErro("Selecione ao menos um bovino");
		}
	}

}
