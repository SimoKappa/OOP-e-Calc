package it.project.SpringBootProject.Model;

public class Report {
	private String country;
	private int refPeriod;
	private String item1;
	private String item2;
	private String code;
	private double value;
	private int extraction;
	private int nca;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getRefPeriod() {
		return refPeriod;
	}
	public void setRefPeriod(int refPeriod) {
		this.refPeriod = refPeriod;
	}
	public String getItem1() {
		return item1;
	}
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	public String getItem2() {
		return item2;
	}
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getExtraction() {
		return extraction;
	}
	public void setExtraction(int extraction) {
		this.extraction = extraction;
	}
	public int getNca() {
		return nca;
	}
	public void setNca(int nca) {
		this.nca = nca;
	}
}
