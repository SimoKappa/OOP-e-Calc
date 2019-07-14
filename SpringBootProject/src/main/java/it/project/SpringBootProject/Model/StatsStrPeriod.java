package it.project.SpringBootProject.Model;

public class StatsStrPeriod {
	private String attributo;
	private String periodo;
	private int occorrenze;
	
	public StatsStrPeriod (String attributo, String periodo, int occorrenze) {
		this.attributo = attributo;
		this.periodo = periodo; 
		this.occorrenze = occorrenze;
	}

	public String getAttributo() {
		return attributo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public int getOccorrenze() {
		return occorrenze;
	}

}
