package it.project.SpringBootProject.Model;

/**
 * statistiche relative agli elementi dell'attributo code
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public class StatsStrCode extends StatsStr{
	private String code;

	/**
	 * costruttore
	 * 
	 * @param attributo  nome dell'attributo richiesto
	 * @param code       valore dell'elemento su cui si contano le occorrenze
	 * @param occorrenze numero delle occorrenze
	 */
	public StatsStrCode(String attributo, String code, int occorrenze) {
		this.attributo = attributo;
		this.code = code;
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
	 * fornisce l'elemento contato
	 * 
	 * @return elemento contato
	 */
	public String getCode() {
		return code;
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
