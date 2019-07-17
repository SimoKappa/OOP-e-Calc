package it.project.SpringBootProject.Service;

public class NativeStats { //classe per implementare il metodo nativo

	private double valori[];
	private int count;
	private double avg, min, max, dev, sum;
	double[] dat = {0, 0, 0, 0, 0};
	
	public native void nativeInt(double[] valori, int i, double[] dat); // prototipo metodo nativo

	public NativeStats(double valoriInt[], int count) {
		this.valori = valoriInt;
		this.count = count;

	}

	public double[] statsDouble(int i) {
		nativeInt(valori, i, dat);//mette in un array i calcoli fatti (to be continued...)
		return dat;
	}
	
	public int getCount() {
		return count;
	}

	public double[] getValoriInt() {
		return valori;
	}

	static {
		System.loadLibrary("NativeStats");
	}

}
