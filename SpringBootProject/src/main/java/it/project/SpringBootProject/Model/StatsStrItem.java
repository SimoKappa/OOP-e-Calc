package it.project.SpringBootProject.Model;

/**
 * statistiche relative agli elementi dell'attributo item
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public class StatsStrItem {
	private String attributo;
	private String item;
	private int occorrenze;

	/**
	 * costruttore
	 * 
	 * @param attributo  nome dell'attributo richiesto
	 * @param item       elemento su cui si contano le occorrenze
	 * @param occorrenze occorrenze
	 */
	public StatsStrItem(String attributo, String item, int occorrenze) {
		this.attributo = attributo;
		this.item = item;
		this.occorrenze = occorrenze;
	}

	/**
	 * fornisce l'attributo su cui si vanno a contare le occorrenze degli elementi
	 * 
	 * @return attributo
	 */
	public String getAttributo() {
		return attributo;
	}

	/**
	 * fornisce l'elemento su cui si contano le occorrenze
	 * 
	 * @return elemento contato
	 */
	public String getItem() {
		return item;
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
