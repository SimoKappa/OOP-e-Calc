package it.project.SpringBootProject.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.project.SpringBootProject.Model.Report;

@Service
public class ReportService {

	private static List<Report> reports = new ArrayList<>();
	
	static
	{
		try
		{
		BufferedReader reader = new BufferedReader(new FileReader("csvfile.csv"));
		try{
		String line;
		int i =0;
		while ((line = reader.readLine())!=null) {
			String [] data = line.split(",");
			//System.out.println(data[0]);
			if (i!=0) {
				/*String countryString = data[0];
				String refPeriodString = data[1];
				String itemString = data[2];
				String codeString = data[3];
				//double value = Double.parseDouble(data[4]);
				double value = 3;
				int extraction = Integer.parseInt(data[5]);
				int nca = Integer.parseInt(data[6]);
				Report recordReport = new Report(countryString, refPeriodString, itemString, codeString, value, extraction, nca);
				*/
				createReport(data);
				//Report recordReport = createReport(data);
				//System.out.println(recordReport.toString());
				}
				//Report recoReport = createReport(attributeStrings);
				//line = reader.readLine();
			i++;
		}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		}catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public List<Report> retrieveallreports()
	{
		return reports;
	}
	
	private static void createReport (String[] data) {
		String countryString = data[0];
		String refPeriodString = data[1];
		String itemString = data[2];
		String codeString = data[3];
		String valueString = data[4];
		String extractionString = data[5];
		int nca = Integer.parseInt(data[6]);
		//return new Report(countryString, refPeriodString, itemString, codeString, valueString, extractionString, nca);
		reports.add(new Report(countryString, refPeriodString, itemString, codeString, valueString, extractionString, nca));
	}
	
}
