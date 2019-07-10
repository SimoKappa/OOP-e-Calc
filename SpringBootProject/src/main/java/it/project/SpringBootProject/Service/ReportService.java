package it.project.SpringBootProject.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import au.com.bytecode.opencsv.CSVReader;
import it.project.SpringBootProject.Model.Report;
//import it.project.SpringBootProject.Model.Metadata;
import it.project.SpringBootProject.Model.StatsNum;
import it.project.SpringBootProject.Model.StatsStr;

@Service
public class ReportService {

	private static List<Report> reports = new ArrayList<>();
	private static List<StatsNum> statsNum = new ArrayList<>();
	private static List<StatsStr> statsStr = new ArrayList<>();

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

	public List<StatsNum> reportsStatsNum(String param) {
		int i = 0;
		double somma = 0, n = 0, min = 0, max = 0, dev = 0, sumdev = 0, dif = 0;
		double avg = 0;
		String valString = new String("value");
		String extString = new String("extraction");
		String ncaString = new String("nca");
		if (param.equals(valString)) {
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
		} else if (param.equals(extString)) {
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
		} else if (param.equals(ncaString)) {
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
		} else {
			statsNum.add(new StatsNum("invalid", 0, 0, 0, 0, 0, 0));
			return statsNum;
		}
		dev = sumdev / n;
		dev = Math.sqrt(dev);
		avg = tronca(avg);
		somma = tronca(somma);
		dev = tronca(dev);
		statsNum.add(new StatsNum(param, avg, min, max, somma, n, dev));
		return statsNum;
	}

	private static double tronca(double a) {
		a *= 10000000;
		a = Math.floor(a);
		a /= 10000000;
		return a;
	}

	public List<StatsStr> reportStatsStr(String param) {
		String refString;
		int count = 0;
		boolean flag;
		String countryString = new String("country");
		String refPreiodString = new String("period");
		String itemString = new String("item");
		String codeString = new String("code");
		if (param.equals(countryString)) {
			for (Report report1 : reports) {
				flag = true;
				refString = report1.getCountry();
				for (StatsStr str : statsStr) {
					String tempString = str.getCountryString();
					if (refString.equals(tempString)) {
						count = 0;
						flag = false;
						break;
					}
				}
				if (flag == true) {
					for (Report report2 : reports) {
						String tempString = report2.getCountry();
						if (refString.equals(tempString))
							count++;
					}
					statsStr.add(new StatsStr(param, refString, null, null, null, count));
				}
			}
		} else if (param.equals(refPreiodString)) {
			for (Report report1 : reports) {
				flag = true;
				refString = report1.getRefPeriod();
				for (StatsStr str : statsStr) {
					String tempString = str.getRefPeriodString();
					if (refString.equals(tempString)) {
						count = 0;
						flag = false;
						break;
					}
				}
				if (flag == true) {
					for (Report report2 : reports) {
						String tempString = report2.getRefPeriod();
						if (refString.equals(tempString))
							count++;
					}
					statsStr.add(new StatsStr(param, null, refString, null, null, count));
				}
			}
		} else if (param.equals(itemString)) {
			for (Report report1 : reports) {
				flag = true;
				refString = report1.getItem();
				for (StatsStr str : statsStr) {
					String tempString = str.getItemString();
					if (refString.equals(tempString)) {
						count = 0;
						flag = false;
						break;
					}
				}
				if (flag == true) {
					for (Report report2 : reports) {
						String tempString = report2.getItem();
						if (refString.equals(tempString))
							count++;
					}
					statsStr.add(new StatsStr(param, null, null, refString, null, count));
				}
			}
		} else if (param.equals(codeString)) {
			for (Report report1 : reports) {
				flag = true;
				refString = report1.getCode();
				for (StatsStr str : statsStr) {
					String tempString = str.getCodeString();
					if (refString.equals(tempString)) {
						count = 0;
						flag = false;
						break;
					}
				}
				if (flag == true) {
					for (Report report2 : reports) {
						String tempString = report2.getCode();
						if (refString.equals(tempString))
							count++;
					}
					statsStr.add(new StatsStr(param, null, null, null, refString, count));
				}
			}
		} else {
			statsStr.add(new StatsStr("invalid", "invalid", "invalid", "invalid", "invalid", 0));
		}
		return statsStr;
	}
}
