package it.project.SpringBootProject.Model;

public class Report {
	private String country;
	private String refperiod;
	private String item;
	private String code;
	private double value;
	private int extraction;
	private int nca;
	
	public Report (String countryString, String refPeriodString, String itemString, String codeString, double value, int extraction, int nca) {
		this.country = countryString;
		this.refperiod = refPeriodString;
		this.item = itemString;
		this.code = codeString;
		this.value = value;
		this.extraction = extraction;
		this.nca = nca;
	}
	
	public String getCountry() {
		return country;
	}
	public String getRefperiod() {
		return refperiod;
	}
	public String getItem() {
		return item;
	}
	public String getCode() {
		return code;
	}
	public double getValue() {
		return value;
	}
	public int getExtraction() {
		return extraction;
	}
	public int getNca() {
		return nca;
	}
}
