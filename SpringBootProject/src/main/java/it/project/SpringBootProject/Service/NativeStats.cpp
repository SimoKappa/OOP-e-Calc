#include <jni.h>
#include "it_project_SpringBootProject_Service_NativeStats.h"
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <math.h>
#include <vector>

using namespace std;

JNIEXPORT void JNICALL Java_it_project_SpringBootProject_Service_NativeStats_nativeInt(JNIEnv *env, jobject obj, jdoubleArray valori, jint j, jdoubleArray dat) {
	jint avg = 0, min = 1, max = 2, dev = 3, sum = 4;
	jdouble *stats = env->GetDoubleArrayElements(dat, NULL);
	jdouble *val = env->GetDoubleArrayElements(valori, NULL);
	jdouble temp = 0, somma = 0, dif = 0, sommadev = 0;

	for (int i = 0; i < j; i++) {
		if (i == 0) {
			stats[min] = stats[max] = val[i];
		}
		else if (val[i] < stats[min]){
			stats[min] = val[i];
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
	env->ReleaseDoubleArrayElements(dat, stats, JNI_COMMIT);
}

JNIEXPORT jobjectArray JNICALL Java_it_project_SpringBootProject_Service_NativeStats_nativeString(JNIEnv * env, jobject obj, jobjectArray array, jint i, jintArray occres, jintArray occdim)
{
	string words[i];
	//converto in un array di stringhe
	for (int k = 0; k < i; k++)
	{
		jobject row = env->GetObjectArrayElement(array, k);
		const char* cvalue = env->GetStringUTFChars((jstring)row, JNI_FALSE);
		words[k] = cvalue;
	}
	//creo i vector in cui inserire rispettivamente parola e occorrenza
	vector<string> app;
	vector<jint> occ;
	bool flag;
	
	for (int k = 0; k < i; k++)
	{
		if (k == 0)
		{
			app.push_back(words[k]);
			occ.push_back(1);
		}
		else
		{
			for (int l = 0; l < app.size(); l++)
			{
				flag = false;

				if ((app.at(l)).compare(words[k]) == 0)
				{
					int tmp = occ.at(l);
					tmp ++;
					occ.erase(occ.begin() + l);
					occ.insert(occ.begin()+l, tmp);
					flag = true;
					break;
				}
			}
			//se la parola non è tra le parole già contate la aggiungo
			if (flag == false)
			{
				app.push_back(words[k]);
				occ.push_back(1);
			}
		}
	}
	//array che utilizzo per riconvertire i vector in array da ripassare all'app Java
	string paroleord[app.size()];
	jint occur[occ.size()];

	for (int k = 0; k < app.size(); k++)
	{
		paroleord[k] = app.at(k);
		occur[k] = occ.at(k);
	}
	jint dimoc[1];
	dimoc[0] = occ.size();
	env->ReleaseIntArrayElements(occres, occur, JNI_COMMIT);
	env->ReleaseIntArrayElements(occdim, dimoc, JNI_COMMIT);
	
	jobjectArray ret;
	ret = (jobjectArray)env->NewObjectArray(app.size(), env->FindClass("java/lang/String"), 0);
	for (int k = 0; k < app.size(); k++)
	{
		env->SetObjectArrayElement(ret, k,env->NewStringUTF(paroleord[k].c_str()));
	}

	return ret;

}