#include <jni.h>
#include "it_project_SpringBootProject_Service_NativeStats.h"
#include <stdio.h>
#include <iostream>
#include <math.h>

using namespace std;

JNIEXPORT jintArray JNICALL Java_it_project_SpringBootProject_Service_NativeStats_nativeInt(JNIEnv *env, jobject obj, jintArray valori, jint j) {
	jclass cls;
	jfieldID fid;
	jint valp;

	cout << "Avviamento metodo per le statistiche native.\n";
	//const jsize = i;
	jint avg = 0, min = 1, max = 2, dev = 3, sum = 4;
	jintArray statsArray = env->NewIntArray(5);// 0 = avg; 1 = min; 2 = max; 3 = dev; 4 = somma;
	jint *val = env->GetIntArrayElements(valori, NULL);
	jint *stats = env->GetIntArrayElements(statsArray, NULL);
	jint temp = 0, somma = 0, dif = 0, sommadev = 0;

	for (int i = 0; i < j; i++) {
		if (i == 0) {
			stats[min] = stats[max] = val[i];
		}
		else if (val[i] < stats[min]){
			stats[min] = stats[min];
		}
		else if (val[i] > stats[max]) {
			stats[max] = val[i];
		}
		somma += val[i];
	}
	stats[sum] = somma;
	stats[avg] = somma / j;
	for (int i = 0; i < j; i++) {
		dif = val[i] - stats[avg];
		dif = pow(dif, 2);
		sommadev += dif;
	}
	
	sommadev /= j;
	stats[dev] = sqrt(sommadev);

	for (int i = 0; i < 5; i++) {
		cout << stats[i] << endl;
	}

	//env->ReleaseIntArrayElements(valori, val, NULL);
	//env->ReleaseIntArrayElements(statsArray, nval, NULL);

	return statsArray;
	/*cls = env->GetObjectClass(obj);
	fid = env->GetFieldId(cls, valoriInt[]);*/
}