package it.project.SpringBootProject.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Model.Metadata;

@Service
public class ReportService {

	private static List<Report> reports = new ArrayList<>();

	static {
		/*
		 * try { BufferedReader reader = new BufferedReader(new
		 * FileReader("csvfile.csv")); try{ String line; int i =0; while ((line =
		 * reader.readLine())!=null) { String [] data = line.split(","); //divide la
		 * stringa quando c'è la virgola for (String aStrings : data) {
		 * aStrings.replaceAll("/\".*?\"/", ""); System.out.println(aStrings); } if
		 * (i!=0) { createReport(data); } i++; } }catch(IOException e) {
		 * e.printStackTrace(); } }catch (FileNotFoundException e) {
		 * e.printStackTrace(); }
		 */

		String fileString = "csvfile.csv";
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(fileString));
			String[] lineStrings;
			int i = 0;
			while ((lineStrings = reader.readNext()) != null) {
				// System.out.println("country = " + lineStrings[0] + " " + lineStrings[1] + " "
				// + lineStrings[2] + " " + lineStrings[3] + " " + lineStrings[4] + " " +
				// lineStrings[5] + " " + lineStrings[6]);
				//lineStrings[2].replaceAll("[\ufffd]", "");
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
		if (data.length == 7)
		{
		reports.add(
				new Report(countryString, refPeriodString, itemString, codeString, valueString, extraction, nca));
		}else {
			reports.add(new Report("Invalid data", "Invalid data", "Invalid data", "Invalid data", 0, 0, 0));
		}
	}

}
