package it.project.SpringBootProject.Model;

public class StatsStrCountry {
	private String attributo;
	private String country;
	private int occorrenze;
	
	public StatsStrCountry (String attributo, String country, int occorrenze) {
		this.attributo = attributo;
		this.country = country;
		this.occorrenze = occorrenze;
	}

	public String getAttributo() {
		return attributo;
	}

	public String getCountry() {
		return country;
	}

	public int getOccorrenze() {
		return occorrenze;
	}

}
