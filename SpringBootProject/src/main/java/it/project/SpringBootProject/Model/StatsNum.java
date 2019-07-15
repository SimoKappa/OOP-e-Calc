package it.project.SpringBootProject.Model;

/**
 * statistiche (media, minimo, massimo, deviazione standard, somma e conta degli
 * elementi) sugli attributi del {@link Report} di tipo numerico quali:<br>
 * <strong> value, extraction, nca</strong>
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public class StatsNum {
	private String data;
	private double avg;
	private double min;
	private double max;
	private double dev;
	private double sum;
	private int count;

	/**
	 * costruttore per le statistiche di tipo numerico
	 * 
	 * @param data  dato su cui si richiedono le statistiche
	 * @param avg   media
	 * @param min   minimo
	 * @param max   massimo
	 * @param sum   somma
	 * @param count conta degli elementi
	 * @param dev   deviazione standard
	 */
	public StatsNum(String data, double avg, double min, double max, double sum, int count, double dev) {
		this.data = data;
		this.avg = avg;
		this.min = min;
		this.max = max;
		this.dev = dev;
		this.sum = sum;
		this.count = count;
	}

	/**
	 * fornisce il dato su cui sono richieste le statistiche
	 * 
	 * @return attributo
	 */
	public String getdata() {
		return data;
	}

	/**
	 * fornisce la media
	 * 
	 * @return media
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * fornisce il minimo
	 * 
	 * @return minimo
	 */
	public double getMin() {
		return min;
	}

	/**
	 * fornisce il massimo
	 * 
	 * @return massimo
	 */
	public double getMax() {
		return max;
	}

	/**
	 * fornisce la deviazione standard
	 * 
	 * @return deviazione standard
	 */
	public double getDev() {
		return dev;
	}

	/**
	 * fornisce la somma di tutti gli elementi di un attributo
	 * 
	 * @return somma
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * fornisce la conta degli elementi
	 * 
	 * @return conta
	 */
	public int getCount() {
		return count;
	}
}
