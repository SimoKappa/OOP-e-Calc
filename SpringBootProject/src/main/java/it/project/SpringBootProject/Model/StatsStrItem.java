package it.project.SpringBootProject.Model;

public class StatsStrItem {
	private String attributo;
	private String item;
	private int occorrenze;
	
	public StatsStrItem (String attributo, String item, int occorrenze) {
		this.attributo = attributo;
		this.item = item;
		this.occorrenze = occorrenze;
	}

	public String getAttributo() {
		return attributo;
	}

	public String getItem() {
		return item;
	}

	public int getOccorrenze() {
		return occorrenze;
	}

}
