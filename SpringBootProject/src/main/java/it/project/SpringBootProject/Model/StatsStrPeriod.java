package it.project.SpringBootProject.Model;

/**
 * statistiche relative agli elementi dell'attributo periodo
 * 
 * @author danilo
 *
 */
public class StatsStrPeriod {
	private String attributo;
	private String periodo;
	private int occorrenze;

	/**
	 * costruttore
	 * 
	 * @param attributo  nome dell'attributo richiesto
	 * @param periodo    elemento su cui si contano le occorrenze
	 * @param occorrenze occorrenze
	 */
	public StatsStrPeriod(String attributo, String periodo, int occorrenze) {
		this.attributo = attributo;
		this.periodo = periodo;
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
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * fornisce il numero di occorrenze
	 * 
	 * @return occorrenze
	 */
	public int getOccorrenze() {
		return occorrenze;
	}

}
