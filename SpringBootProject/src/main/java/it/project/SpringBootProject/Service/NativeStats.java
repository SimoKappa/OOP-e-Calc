package it.project.SpringBootProject.Service;

public class NativeStats { //classe per implementare il metodo nativo

	private double valori[];
	private String parole[];
	private int count;
	private double avg, min, max, dev, sum;
	double[] dat = {0, 0, 0, 0, 0};
	
	
	public native void nativeInt(double[] valori, int i, double[] dat); // prototipo metodo nativo
	
	public native void nativeString(String[] parole, int i);

	public NativeStats(double valoriInt[], int count) {
		this.valori = valoriInt;
		this.count = count;
	}

	public NativeStats()
	{}

	
	public double[] statsDouble(int i) {
		nativeInt(valori, i, dat);
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
