package it.project.SpringBootProject.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

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
		nativeInt(valoriInt, i);//mette in un array i calcoli fatti (to be continued...)
		for (int j = 0; j<5; j++) {
		//System.out.println(buf[j]);
		}
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
