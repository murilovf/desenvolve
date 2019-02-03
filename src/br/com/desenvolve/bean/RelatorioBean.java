package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.desenvolve.dao.RelatorioDAO;
import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Dieta;
import br.com.desenvolve.domain.DietaItem;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.domain.Medida;
import br.com.desenvolve.domain.Vacina;
import br.com.desenvolve.util.JSFUtil;
import br.com.desenvolve.util.RelatorioUtil;

@ManagedBean(name = "MBRelatorio")
@ViewScoped
public class RelatorioBean {
	private Date dataInicial;
	private Date dataFinal;
	private Date dataInicial2;
	private Date dataFinal2;

	// VARIAVEIS PARA RELATORIO DE BOVINOS
	private ArrayList<Bovino> listaBovinos;
	private Bovino bovinoFiltro = new Bovino();
	private String ordem = "codigo";

	// VARIAVEIS PARA RELATORIO DE VACINAS POR BOVINO
	private ArrayList<Vacina> listaVacinas;
	private Vacina vacinaFiltro = new Vacina();
	private boolean aplicado = true;
	private boolean naoAplicado = true;

	// VARIAVEIS PARA RELATÓRIO DE MEDIDAS
	private Medida medidaFiltro = new Medida();
	private ArrayList<Medida> listaMedidas;
	private Double pesoInicial, pesoFinal, alturaInicial, alturaFinal, circunferenciaInicial, circunferenciaFinal;

	// VARIAVEIS PARA RELATORIO DE MEDICAMENTOS POR BOVINO
	private ArrayList<Medicamento> listaMedicamentos;
	private Medicamento medicamentoFiltro = new Medicamento();
	private Double dosagemInicial, dosagemFinal;
	
	// VARIAVEI PARA RELATORIO DE DIETAS POR BOVINO
	private ArrayList<Dieta> listaDietas;
	private ArrayList<DietaItem> listaDietaItens;
	private Dieta dietaFiltro = new Dieta();
	private Alimento alimentoFiltro = new Alimento();
	private Double valorInicial, valorFinal;
	
	
	public ArrayList<DietaItem> getListaDietaItens() {
		return listaDietaItens;
	}

	public void setListaDietaItens(ArrayList<DietaItem> listaDietaItens) {
		this.listaDietaItens = listaDietaItens;
	}

	public Double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public Alimento getAlimentoFiltro() {
		return alimentoFiltro;
	}

	public void setAlimentoFiltro(Alimento alimentoFiltro) {
		this.alimentoFiltro = alimentoFiltro;
	}

	public ArrayList<Dieta> getListaDietas() {
		return listaDietas;
	}

	public void setListaDietas(ArrayList<Dieta> listaDietas) {
		this.listaDietas = listaDietas;
	}

	public Dieta getDietaFiltro() {
		return dietaFiltro;
	}

	public void setDietaFiltro(Dieta dietaFiltro) {
		this.dietaFiltro = dietaFiltro;
	}

	public Double getDosagemInicial() {
		return dosagemInicial;
	}

	public void setDosagemInicial(Double dosagemInicial) {
		this.dosagemInicial = dosagemInicial;
	}

	public Double getDosagemFinal() {
		return dosagemFinal;
	}

	public void setDosagemFinal(Double dosagemFinal) {
		this.dosagemFinal = dosagemFinal;
	}

	public Date getDataInicial2() {
		return dataInicial2;
	}

	public void setDataInicial2(Date dataInicial2) {
		this.dataInicial2 = dataInicial2;
	}

	public Date getDataFinal2() {
		return dataFinal2;
	}

	public void setDataFinal2(Date dataFinal2) {
		this.dataFinal2 = dataFinal2;
	}

	public Medicamento getMedicamentoFiltro() {
		return medicamentoFiltro;
	}

	public void setMedicamentoFiltro(Medicamento medicamentoFiltro) {
		this.medicamentoFiltro = medicamentoFiltro;
	}

	public ArrayList<Medicamento> getListaMedicamentos() {
		return listaMedicamentos;
	}

	public void setListaMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}

	public Medida getMedidaFiltro() {
		return medidaFiltro;
	}

	public void setMedidaFiltro(Medida medidaFiltro) {
		this.medidaFiltro = medidaFiltro;
	}

	public ArrayList<Medida> getListaMedidas() {
		return listaMedidas;
	}

	public void setListaMedidas(ArrayList<Medida> listaMedidas) {
		this.listaMedidas = listaMedidas;
	}

	public Double getPesoInicial() {
		return pesoInicial;
	}

	public void setPesoInicial(Double pesoInicial) {
		this.pesoInicial = pesoInicial;
	}

	public Double getPesoFinal() {
		return pesoFinal;
	}

	public void setPesoFinal(Double pesoFinal) {
		this.pesoFinal = pesoFinal;
	}

	public Double getAlturaInicial() {
		return alturaInicial;
	}

	public void setAlturaInicial(Double alturaInicial) {
		this.alturaInicial = alturaInicial;
	}

	public Double getAlturaFinal() {
		return alturaFinal;
	}

	public void setAlturaFinal(Double alturaFinal) {
		this.alturaFinal = alturaFinal;
	}

	public Double getCircunferenciaInicial() {
		return circunferenciaInicial;
	}

	public void setCircunferenciaInicial(Double circunferenciaInicial) {
		this.circunferenciaInicial = circunferenciaInicial;
	}

	public Double getCircunferenciaFinal() {
		return circunferenciaFinal;
	}

	public void setCircunferenciaFinal(Double circunferenciaFinal) {
		this.circunferenciaFinal = circunferenciaFinal;
	}

	public boolean isAplicado() {
		return aplicado;
	}

	public void setAplicado(boolean aplicado) {
		this.aplicado = aplicado;
	}

	public boolean isNaoAplicado() {
		return naoAplicado;
	}

	public void setNaoAplicado(boolean naoAplicado) {
		this.naoAplicado = naoAplicado;
	}

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Bovino getBovinoFiltro() {
		return bovinoFiltro;
	}

	public void setBovinoFiltro(Bovino bovinoFiltro) {
		this.bovinoFiltro = bovinoFiltro;
	}

	public ArrayList<Bovino> getListaBovinos() {
		return listaBovinos;
	}

	public void setListaBovinos(ArrayList<Bovino> listaBovinos) {
		this.listaBovinos = listaBovinos;
	}

	public ArrayList<Vacina> getListaVacinas() {
		return listaVacinas;
	}

	public void setListaVacinas(ArrayList<Vacina> listaVacinas) {
		this.listaVacinas = listaVacinas;
	}

	public Vacina getVacinaFiltro() {
		return vacinaFiltro;
	}

	public void setVacinaFiltro(Vacina vacinaFiltro) {
		this.vacinaFiltro = vacinaFiltro;
	}

	public void gerarRelatorioBovino() {

		try {
			RelatorioDAO dao = new RelatorioDAO();

			listaBovinos = dao.listarBovino(bovinoFiltro, dataInicial, dataFinal, ordem);

			String subtitulo = subTituloRelatorioBovinos();

			RelatorioUtil.gerarRelatorioBovino(listaBovinos, subtitulo);

		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}

	}

	public void gerarRelatorioVacinasPorBovino() {

		vacinaFiltro.setBovino(bovinoFiltro);

		try {
			RelatorioDAO dao = new RelatorioDAO();

			listaVacinas = dao.listarVacinas(vacinaFiltro, dataInicial, dataFinal, aplicado, naoAplicado);

			String subtitulo = subTituloRelatorioVacinasPorBovino();

			if (listaVacinas != null) {
				RelatorioUtil.gerarRelatorioVacinasPorBovino(listaVacinas, subtitulo);
			} else {
				JSFUtil.adicionarMensagemErro("Não existe dados para o filtro informado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}

	}

	public void gerarRelatorioMedidas() {

		medidaFiltro.setBovino(bovinoFiltro);

		try {
			RelatorioDAO dao = new RelatorioDAO();

			listaMedidas = dao.listarMedidas(medidaFiltro, dataInicial, dataFinal, pesoInicial, pesoFinal,
					alturaInicial, alturaFinal, circunferenciaInicial, circunferenciaFinal);

			String subtitulo = subTituloRelatorioMedidas();

			if (listaMedidas != null) {
				RelatorioUtil.gerarRelatorioMedidas(listaMedidas, subtitulo);
			} else {
				JSFUtil.adicionarMensagemErro("Não existe dados para o filtro informado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}

	}

	public void gerarRelatorioMedicamentosPorBovino() {
		medicamentoFiltro.setBovino(bovinoFiltro);

		try {
			RelatorioDAO dao = new RelatorioDAO();

			listaMedicamentos = dao.listarMedicamentos(medicamentoFiltro, dataInicial, dataFinal, dataInicial2,
					dataFinal2, dosagemInicial, dosagemFinal);

			String subtitulo = subTituloRelatorioMedicamentosPorBovino();

			if (listaMedicamentos != null) {
				RelatorioUtil.gerarRelatorioMedicamentosPorBovino(listaMedicamentos, subtitulo);
			} else {
				JSFUtil.adicionarMensagemErro("Não existe dados para o filtro informado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void gerarRelatorioDietasPorBovino() {
		dietaFiltro.setBovino(bovinoFiltro);
		

		try {
			RelatorioDAO dao = new RelatorioDAO();

			listaDietaItens = dao.listarDietas(dietaFiltro, alimentoFiltro, dataInicial, dataFinal, dataInicial2,
					dataFinal2, valorInicial, valorFinal);

			String subtitulo = subTituloRelatorioDietasPorBovino();

			if (listaDietaItens != null) {
				RelatorioUtil.gerarRelatorioDietasPorBovino(listaDietaItens, subtitulo);
			} else {
				JSFUtil.adicionarMensagemErro("Não existe dados para o filtro informado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String subTituloRelatorioBovinos() {
		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
		String subtitulo = "";

		if (bovinoFiltro.getNome().trim().length() > 0) {
			subtitulo += "BOVINO: " + bovinoFiltro.getNome() + " / ";
		}

		if (bovinoFiltro.getOrigem().trim().length() > 0) {
			subtitulo += "ORIGEM: " + bovinoFiltro.getOrigem() + " / ";
		}
		
		if (bovinoFiltro.getSituacao() != 2) {
			String situacao = bovinoFiltro.getSituacao() == 1 ? "ATIVOS" : "INATIVOS";
			subtitulo += "SITUAÇÃO: " + situacao  + " / ";
		}

		if (bovinoFiltro.getRaca().trim().length() > 0) {
			subtitulo += "RAÇA: " + bovinoFiltro.getRaca() + " / ";
		}

		if (dataInicial != null && dataFinal != null) {
			subtitulo += "DATA DE NASCIMENTO: " + out.format(dataInicial) + " à " + out.format(dataFinal) + " / ";
		}

		if (subtitulo.length() == 0) {
			subtitulo = "TODOS";
		} else {
			subtitulo = subtitulo.substring(0, subtitulo.length() - 2);
		}

		if (ordem.equals("codigo")) {
			subtitulo += "\nORDENADO POR: CÓDIGO";
		} else if (ordem.equals("nome")) {
			subtitulo += "\nORDENADO POR: NOME";
		} else if (ordem.equals("origem")) {
			subtitulo += "\nORDENADO POR: ORIGEM";
		} else if (ordem.equals("raca")) {
			subtitulo += "\nORDENADO POR: RAÇA";
		} else if (ordem.equals("data")) {
			subtitulo += "\nORDENADO POR: DATA DE NASCIMENTO";
		}

		return subtitulo;
	}

	public String subTituloRelatorioVacinasPorBovino() {

		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
		String subtitulo = "";

		if (vacinaFiltro.getNome().trim().length() > 0) {
			subtitulo += "VACINA: " + vacinaFiltro.getNome() + " / ";
		}

		if (vacinaFiltro.getBovino().getNome().trim().length() > 0) {
			subtitulo += "BOVINO: " + vacinaFiltro.getBovino().getNome() + " / ";
		}
		
		if (vacinaFiltro.getFinalidade().trim().length() > 0) {
			subtitulo += "FINALIDADE: " + vacinaFiltro.getFinalidade() + " / ";
		}

		if (dataInicial != null && dataFinal != null) {
			subtitulo += "DATA DA VACINA: " + out.format(dataInicial) + " à " + out.format(dataFinal) + " / ";
		}

		if (aplicado && naoAplicado) {
			subtitulo += "";
		} else if (aplicado) {
			subtitulo += "SITUAÇÃO DA VACINA : APLICADO / ";
		} else if (naoAplicado) {
			subtitulo += "SITUAÇÃO DA VACINA : NÃO APLICADO / ";
		}

		if (subtitulo.length() == 0) {
			subtitulo = "TODOS";
		} else {
			subtitulo = subtitulo.substring(0, subtitulo.length() - 2);
		}

		return subtitulo;
	}

	public String subTituloRelatorioMedidas() {

		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
		String subtitulo = "";

		if (medidaFiltro.getBovino().getNome().trim().length() > 0) {
			subtitulo += "BOVINO: " + medidaFiltro.getBovino().getNome() + " / ";
		}

		if (dataInicial != null && dataFinal != null) {
			subtitulo += "DATA DA MEDIDA: " + out.format(dataInicial) + " à " + out.format(dataFinal) + " / ";
		}

		if (pesoInicial != null && pesoFinal != null) {
			subtitulo += "PESO: " + pesoInicial.toString().replace(".", ",") + " à "
					+ pesoFinal.toString().replace(".", ",") + " / ";
		}

		if (alturaInicial != null && alturaFinal != null) {
			subtitulo += "ALTURA: " + alturaInicial.toString().replace(".", ",") + " à "
					+ alturaFinal.toString().replace(".", ",") + " / ";
		}

		if (circunferenciaInicial != null && circunferenciaFinal != null) {
			subtitulo += "CIRCUNFERÊNCIA: " + circunferenciaInicial.toString().replace(".", ",") + " à "
					+ circunferenciaFinal.toString().replace(".", ",") + " / ";
		}

		if (subtitulo.length() == 0) {
			subtitulo = "TODOS";
		} else {
			subtitulo = subtitulo.substring(0, subtitulo.length() - 2);
		}

		return subtitulo;
	}
	
	public String subTituloRelatorioMedicamentosPorBovino() {

		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
		String subtitulo = "";

		if (medicamentoFiltro.getNome().trim().length() > 0) {
			subtitulo += "MEDICAMENTO: " + medicamentoFiltro.getNome() + " / ";
		}

		if (medicamentoFiltro.getBovino().getNome().trim().length() > 0) {
			subtitulo += "BOVINO: " + medicamentoFiltro.getBovino().getNome() + " / ";
		}

		if (dataInicial != null && dataFinal != null) {
			subtitulo += "DATA INICIAL DO MEDICAMENTO: " + out.format(dataInicial) + " à " + out.format(dataFinal) + " / ";
		}
		
		if (dataInicial2 != null && dataFinal2 != null) {
			subtitulo += "DATA FINAL DO MEDICAMENTO: " + out.format(dataInicial2) + " à " + out.format(dataFinal2) + " / ";
		}
		
		if (medicamentoFiltro.getTipo().trim().length() > 0) {
			subtitulo += "TIPO: " + medicamentoFiltro.getTipo() + " / ";
		}
		
		if (dosagemInicial != null && dosagemFinal != null) {
			subtitulo += "DOSAGEM: " + dosagemInicial.toString().replace(".", ",") + " à "
					+ dosagemFinal.toString().replace(".", ",") + " / ";
		}

		if (subtitulo.length() == 0) {
			subtitulo = "TODOS";
		} else {
			subtitulo = subtitulo.substring(0, subtitulo.length() - 2);
		}

		return subtitulo;
	}
	
	public String subTituloRelatorioDietasPorBovino() {

		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
		String subtitulo = "";

		if (dietaFiltro.getNome().trim().length() > 0) {
			subtitulo += "DIETA: " + dietaFiltro.getNome() + " / ";
		}

		if (dietaFiltro.getBovino().getNome().trim().length() > 0) {
			subtitulo += "BOVINO: " + dietaFiltro.getBovino().getNome() + " / ";
		}

		if (dataInicial != null && dataFinal != null) {
			subtitulo += "DATA INICIAL DA DIETA: " + out.format(dataInicial) + " à " + out.format(dataFinal) + " / ";
		}
		
		if (dataInicial2 != null && dataFinal2 != null) {
			subtitulo += "DATA FINAL DA DIETA: " + out.format(dataInicial2) + " à " + out.format(dataFinal2) + " / ";
		}
		
		if (alimentoFiltro.getDescricao().trim().length() > 0) {
			subtitulo += "ALIMENTO: " + alimentoFiltro.getDescricao() + " / ";
		}
		
		if (valorInicial != null && valorFinal != null) {
			subtitulo += "VALOR TOTAL: " + valorInicial.toString().replace(".", ",") + " à "
					+ valorFinal.toString().replace(".", ",") + " / ";
		}

		if (subtitulo.length() == 0) {
			subtitulo = "TODOS";
		} else {
			subtitulo = subtitulo.substring(0, subtitulo.length() - 2);
		}

		return subtitulo;
	}


	public void limparFiltros() {
		bovinoFiltro = new Bovino();
		vacinaFiltro = new Vacina();
		medidaFiltro = new Medida();
		dietaFiltro = new Dieta();
		alimentoFiltro = new Alimento();
		medicamentoFiltro = new Medicamento();
		dataInicial = null;
		dataFinal = null;
		dataInicial2 = null;
		dataFinal2 = null;
		ordem = "codigo";
		aplicado = true;
		naoAplicado = true;
		pesoInicial = null;
		pesoFinal = null;
		alturaInicial = null;
		alturaFinal = null;
		circunferenciaInicial = null;
		circunferenciaFinal = null;
		dosagemInicial = null;
		dosagemFinal = null;
		valorInicial = null;
		valorFinal = null;
	}

}
