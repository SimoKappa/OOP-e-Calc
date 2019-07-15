package it.project.SpringBootProject.Model;

/**
 * Classe che permette di accogliere i dati dal dataset e creare i relativi
 * oggetti
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public class Report {

	// attributi

	private String country;
	private String refperiod;
	private String item;
	private String code;
	private double value;
	private int extraction;
	private int nca;

	// metodi

	/**
	 * Crea un record
	 * 
	 * @param country    nazione
	 * @param refperiod  periodo di riferimento
	 * @param item       articolo
	 * @param code       codice articolo
	 * @param value      valore
	 * @param extraction data di estrazione
	 * @param nca        numero di comunicazioni
	 */
	public Report(String country, String refperiod, String item, String code, double value, int extraction, int nca) {
		this.country = country;
		this.refperiod = refperiod;
		this.item = item;
		this.code = code;
		this.value = value;
		this.extraction = extraction;
		this.nca = nca;
	}

	/**
	 * fornisce la nazione del record
	 * 
	 * @return nazione
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * fornisce il periodo di riferimento del record
	 * 
	 * @return periodo di riferimento
	 */
	public String getRefperiod() {
		return refperiod;
	}

	/**
	 * fornisce l'articolo del record
	 * 
	 * @return l'articolo
	 */
	public String getItem() {
		return item;
	}

	/**
	 * fornisce il codice del record
	 * 
	 * @return il codice
	 */
	public String getCode() {
		return code;
	}

	/**
	 * fornisce il valore del record
	 * 
	 * @return il valore
	 */
	public double getValue() {
		return value;
	}

	/**
	 * fornisce la data di estrazione
	 * 
	 * @return data di estrazione
	 */
	public int getExtraction() {
		return extraction;
	}

	/**
	 * fornisce il numero di comunicazioni
	 * 
	 * @return il numero di comunicazioni
	 */
	public int getNca() {
		return nca;
	}

}
