package it.project.SpringBootProject.Model;

public class StatsNum {
	private String data;
	private double avg;
	private double min;
	private double max;
	private double dev;
	private double sum;
	private double count;
	
	public StatsNum (String data, double avg, double min, double max, double sum, double count, double dev) {
		this.data = data;
		this.avg = avg;
		this.min = min;
		this.max = max;
		this.dev = dev;
		this.sum = sum;
		this.count = count;
	}
	
	public String getdata() {
		return data;
	}

	public double getAvg() {
		return avg;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getDev() {
		return dev;
	}

	public double getSum() {
		return sum;
	}

	public double getCount() {
		return count;
	}
}
