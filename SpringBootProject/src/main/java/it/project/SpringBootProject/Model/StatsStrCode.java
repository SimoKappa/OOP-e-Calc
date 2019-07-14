package it.project.SpringBootProject.Model;

public class StatsStrCode {
	private String attributo;
	private String code;
	private int occorrenze;
	
	public StatsStrCode (String attributo, String code, int occorrenze) {
		this.attributo = attributo;
		this.code = code;
		this.occorrenze = occorrenze;
	}

	public String getAttributo() {
		return attributo;
	}

	public String getCode() {
		return code;
	}

	public int getOccorrenze() {
		return occorrenze;
	}

}
