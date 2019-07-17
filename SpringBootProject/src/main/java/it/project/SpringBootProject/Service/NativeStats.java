package it.project.SpringBootProject.Service;

import java.lang.reflect.Array;

public class NativeStats { //classe per implementare il metodo nativo

	// attriuti e inizializzazioni
	private int valoriInt[];

	private int count;

	public native int[] nativeInt(int[] valori, int i); // prototipo metodo nativo
	// (NativeStats ob = new NativeStats();

	public NativeStats(int valoriInt[], int count) {
		this.valoriInt = valoriInt;
		this.count = count;

	}

	// metodi
	public void chiamata(int i) {
		int[] buf = this.nativeInt(valoriInt, i);//mette in un array i calcoli fatti (to be continued...)
	}

	public int getCount() {
		return count;
	}

	public int[] getValoriInt() {
		return valoriInt;
	}

	static {
		System.loadLibrary("NativeStats");
	}

}
