package br.com.desenvolve.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.desenvolve.dao.MedicamentoDAO;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBMedicamentoGeralMed")
@ViewScoped
public class MedicamentoGeralMedicamentoBean {
	
	private ArrayList<Medicamento> listaMedicamentos;
	
	
	public ArrayList<Medicamento> getListaMedicamentos() {
		return listaMedicamentos;
	}



	public void setListaMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}



	public void carregaPesquisa(){

		listaMedicamentos = (ArrayList<Medicamento>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selec");
					
	}
	
	public void salvar() throws ParseException{
		
		try {
			MedicamentoDAO dao = new MedicamentoDAO();
			dao.salvarParaTodos(listaMedicamentos);
			JSFUtil.adicionarMensagemSucesso("Medicamentos Salvos com Sucesso");
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
		
	}

}
