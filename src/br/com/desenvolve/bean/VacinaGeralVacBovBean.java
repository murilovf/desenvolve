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
import br.com.desenvolve.dao.VacinaDAO;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Vacina;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBVacinaGeralVacBov")
@ViewScoped
public class VacinaGeralVacBovBean {
	
	private Vacina vacina;
	private String data;
	private String dataAplicacao;
	private ArrayList<Vacina> listaVacinas;
	private ArrayList<Bovino> bovinos;
	private ArrayList<Bovino> bovinosSelecionados;
	private boolean controlaCampo = false;

	public String getDataAplicacao() {
		return dataAplicacao;
	}

	public void setDataAplicacao(String dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}

	public ArrayList<Vacina> getListaVacinas() {
		return listaVacinas;
	}

	public void setListaVacinas(ArrayList<Vacina> listaVacinas) {
		this.listaVacinas = listaVacinas;
	}

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}	
	public boolean isControlaCampo() {
		return controlaCampo;
	}

	public void setControlaCampo(boolean controlaCampo) {
		this.controlaCampo = controlaCampo;
	}

	public void prepararNovo() {

		vacina = new Vacina();
		data = null;

	}
	
	public void carregaPesquisa() throws SQLException{
		BovinoDAO dao = new BovinoDAO();
		bovinos = dao.listarAtivos();
		listaVacinas = new ArrayList<>();
		if (!controlaCampo){
			vacina = new Vacina();
		}
		data=null;
		dataAplicacao=null;
					
	}
	
	
	public void salvar() throws ParseException{
		
		if(bovinosSelecionados.size()>0){
			
			vacina.setDatavacina(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			if(vacina.getSituacao()==1){
				vacina.setDataaplicacao(new SimpleDateFormat("dd/MM/yyyy").parse(dataAplicacao));
			}
			for (int i = 0; i < bovinosSelecionados.size(); i++) {
				
				Vacina vacinaAux = new Vacina();
				vacinaAux.setBovino(bovinosSelecionados.get(i));
				vacinaAux.setNome(vacina.getNome());
				vacinaAux.setDatavacina(vacina.getDatavacina());
				vacinaAux.setDataaplicacao(vacina.getDataaplicacao());
				vacinaAux.setFinalidade(vacina.getFinalidade());
				vacinaAux.setSituacao(vacina.getSituacao());
				
				listaVacinas.add(vacinaAux);
				
			}

		
			
			try {
				VacinaDAO dao = new VacinaDAO();
				dao.salvarParaTodos(listaVacinas);
				JSFUtil.adicionarMensagemSucesso("Vacina Salva com Sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				ec.redirect("./rebanho.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	
		}else{
			JSFUtil.adicionarMensagemErro("Selecione ao menos um bovino");
		}
	}
	
	public void controlarCampo(){
		controlaCampo = true;
		
	}

}
