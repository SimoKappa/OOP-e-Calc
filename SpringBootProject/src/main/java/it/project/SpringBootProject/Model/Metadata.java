package it.project.SpringBootProject.Model;

/**
 * metadati degli attributi del dataset
 * 
 * @author danilo
 *
 */
public class Metadata {
	private String alias;
	private String sourceField;
	private String type;

	/**
	 * costruttore
	 * 
	 * @param alias       attributo
	 * @param sourceField descrizione dell'attributo
	 * @param type        tipo
	 */
	public Metadata(String alias, String sourceField, String type) {
		this.alias = alias;
		this.sourceField = sourceField;
		this.type = type;
	}

	/**
	 * restituisce il nome dell'attributo
	 * 
	 * @return attributo
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * restituisce la descrizione dell'attributo
	 * 
	 * @return
	 */
	public String getSourceField() {
		return sourceField;
	}

	/**
	 * restituisce il tipo dell'attributo
	 * 
	 * @return tipo
	 */
	public String getType() {
		return type;
	}

}
