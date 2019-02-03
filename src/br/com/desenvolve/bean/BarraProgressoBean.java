package br.com.desenvolve.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class BarraProgressoBean {

	private Integer progress;

	public Integer getProgress() {
		if (progress == null) {
			progress = 0;
		} else {
			progress = progress + (int) (1 * 35);

			if (progress > 100)
				progress = 100;
		}

		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public void cancel() {
		progress = null;
	}

}
