#include <jni.h>
#include "it_project_SpringBootProject_Service_NativeStats.h"
#include <stdio.h>
#include <iostream>

using namespace std;

JNIEXPORT jintArray JNICALL Java_it_project_SpringBootProject_Service_NativeStats_nativeInt(JNIEnv *env, jobject obj, jintArray valori) {
	jclass cls;
	jfieldID fid;
	jint valp;

	cout << "Avviamento metodo per le statistiche native.\n";
	const jsize length = env->GetArrayLength(valori);
	jintArray statsArray = env->NewIntArray(length);
	jint *val = env->GetIntArrayElements(valori, NULL);
	jint *nval = env->GetIntArrayElements(statsArray, NULL);

	for (int i = 0, n = length - 1; i < length; i++, n--) {
		nval[n] = val[i];
	}

	//env->ReleaseIntArrayElements(valori, val, NULL);
	//env->ReleaseIntArrayElements(statsArray, nval, NULL);

	return statsArray;
	/*cls = env->GetObjectClass(obj);
	fid = env->GetFieldId(cls, valoriInt[]);*/
}