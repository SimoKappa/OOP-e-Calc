package it.project.SpringBootProject.Model;

/**
 * statistiche relative agli elementi dell'attributo country
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public class StatsStrCountry extends StatsStr{
	private String country;

	/**
	 * costruttore
	 * 
	 * @param attributo  attributo su cui si contano gli elementi
	 * @param country    elemento contato
	 * @param occorrenze occorrenze
	 */
	public StatsStrCountry(String attributo, String country, int occorrenze) {
		this.attributo = attributo;
		this.country = country;
		this.occorrenze = occorrenze;
	}

	/**
	 * fornisce l'attributo su cui vengono contati gli elementi
	 * 
	 * @return attributo
	 */
	public String getAttributo() {
		return attributo;
	}

	/**
	 * fornisce l'elemento contato
	 * 
	 * @return elemento contato
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * fornisce le occorrenze
	 * 
	 * @return occorrenze
	 */
	public int getOccorrenze() {
		return occorrenze;
	}

}
