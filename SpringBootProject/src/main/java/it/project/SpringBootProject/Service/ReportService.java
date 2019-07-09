package it.project.SpringBootProject.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import au.com.bytecode.opencsv.CSVReader;
import it.project.SpringBootProject.Model.Report;
//import it.project.SpringBootProject.Model.Metadata;
import it.project.SpringBootProject.Model.Stats;

@Service
public class ReportService {

	private static List<Report> reports = new ArrayList<>();
	private static List<Stats> stats = new ArrayList<>();

	public void init() {
		String fileString = "csvfile.csv";
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(fileString));
			String[] lineStrings;
			int i = 0;
			while ((lineStrings = reader.readNext()) != null) {
				lineStrings[2] = lineStrings[2].replaceAll("\uFFFD", "");
				if (i != 0) {
					createReport(lineStrings);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Report> retrieveallreports() {
		return reports;
	}

	private static void createReport(String[] data) {
		String countryString = data[0];
		String refPeriodString = data[1];
		String itemString = data[2];
		String codeString = data[3];
		Double valueString = Double.parseDouble(data[4]);
		int extraction = Integer.parseInt(data[5]);
		int nca = Integer.parseInt(data[6]);
		if (data.length == 7) {
			reports.add(
					new Report(countryString, refPeriodString, itemString, codeString, valueString, extraction, nca));
		} else {
			reports.add(new Report("Invalid data", "Invalid data", "Invalid data", "Invalid data", 0, 0, 0));
		}
	}

	public List<Stats> reportsStats(String param) {
		int i = 0;
		double somma = 0, n = 0, min = 0, max = 0, dev = 0, sumdev = 0, dif = 0;
		double avg = 0;
		String valString = new String("value");
		String extString = new String("extraction");
		String ncaString = new String("nca");
		if (param.equals(valString))
			i = 1;
		else if (param.equals(extString))
			i = 2;
		else if (param.equals(ncaString))
			i = 3;
		else return stats;
		switch (i) {
		case 1:
			for (Report report : reports) {
				somma += report.getValue();
				if (n == 0)
					max = min = report.getValue();
				else if (report.getValue() < min)
					min = report.getValue();
				else if (report.getValue() > max)
					max = report.getValue();
				
				n++;
			}
			avg = somma / n;
			for (Report report : reports) {
				dif = report.getValue() - avg;
				dif = Math.pow(dif, 2);
				sumdev += dif;
			}
			break;
		case 2:
			for (Report report : reports) {
				somma += report.getExtraction();
				if (n == 0)
					max = min = report.getExtraction();
				else if (report.getExtraction() < min)
					min = report.getExtraction();
				else if (report.getExtraction() > max)
					max = report.getExtraction();
				n++;
			}
			avg = somma / n;
			for (Report report : reports) {
				dif = report.getExtraction() - avg;
				dif = Math.pow(dif, 2);
				sumdev += dif;
			}
			break;
		case 3:
			for (Report report : reports) {
				somma += report.getNca();
				if (n == 0)
					max = min = report.getNca();
				else if (report.getNca() < min)
					min = report.getNca();
				else if (report.getNca() > max)
					max = report.getNca();
				n++;
			}
			avg = somma / n;
			for (Report report : reports) {
				dif = report.getNca() - avg;
				dif = Math.pow(dif, 2);
				sumdev += dif;
			}
			break;
		default:
			break;
		}
		dev = sumdev / n;
		dev = Math.sqrt(dev);
		avg = tronca(avg);
		somma = tronca(somma);
		dev = tronca(dev);
		stats.add(new Stats(param, avg, min, max, somma, n, dev));
		return stats;
	}

	private static double tronca(double a) {
		a *= 10000000;
		a = Math.floor(a);
		a /= 10000000;
		return a;
	}
}
