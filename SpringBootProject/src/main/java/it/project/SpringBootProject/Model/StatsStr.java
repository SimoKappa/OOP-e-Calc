package it.project.SpringBootProject.Model;

public class StatsStr {
	private String dataString;
	private String countryString;
	private String refPeriodString;
	private String itemString;
	private String codeString;
	private int occ;
	
	public StatsStr (String dataString, String countryString, String refPeriodString, String itemString, String codeString, int count) {
		this.dataString = dataString;
		this.countryString = countryString;
		this.refPeriodString = refPeriodString;
		this.itemString = itemString;
		this.codeString = codeString;
		this.occ = count;
	}
	
	public StatsStr (String dataString, String countryString, int count) {
		this.dataString = dataString;
		this.countryString = countryString;
		this.occ = count;
	}

	public String getDataString() {
		return dataString;
	}

	public String getCountryString() {
		return countryString;
	}

	public String getRefPeriodString() {
		return refPeriodString;
	}

	public String getItemString() {
		return itemString;
	}

	public String getCodeString() {
		return codeString;
	}

	public int getOcc() {
		return occ;
	}

}
