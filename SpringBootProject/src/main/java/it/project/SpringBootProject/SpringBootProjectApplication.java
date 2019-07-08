package it.project.SpringBootProject;

import java.awt.List;
import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.management.loading.PrivateClassLoader;

import org.apache.catalina.startup.CertificateCreateRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.StaticApplicationContext;

import it.project.SpringBootProject.Model.Report;

@ComponentScan(basePackages={"it.project.SpringBootProject.Controller"})  //va a cercare il controller nel package corretto
@SpringBootApplication
public class SpringBootProjectApplication {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		SpringApplication.run(SpringBootProjectApplication.class, args);

		
		/*Path fileString = Paths.get("csvfile.csv");
		
		BufferedReader br = Files.newBufferedReader(fileString);
		String lineString = br.readLine();
		
		while (lineString != null) {
			String [] attributeStrings = lineString.split(",");
			Report recordReport = createReport(attributeStrings);
		}*/
		
		
		
		BufferedReader reader = new BufferedReader(new FileReader("csvfile.csv"));
		String line = reader.readLine();
		int i =0;
		while (line!=null) {
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
				Report recordReport = createReport(data);
				System.out.println(recordReport.toString());
				}
				//Report recoReport = createReport(attributeStrings);
				line = reader.readLine();
			i++;
		}
	}
	
	private static Report createReport (String[] data) {
		String countryString = data[0];
		String refPeriodString = data[1];
		String itemString = data[2];
		String codeString = data[3];
		String valueString = data[4];
		String extractionString = data[5];
		int nca = Integer.parseInt(data[6]);
		return new Report(countryString, refPeriodString, itemString, codeString, valueString, extractionString, nca);
	}

}
