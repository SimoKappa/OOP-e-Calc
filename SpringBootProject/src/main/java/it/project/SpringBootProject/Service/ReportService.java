package it.project.SpringBootProject.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import au.com.bytecode.opencsv.CSVReader;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Model.StatsNum;
import it.project.SpringBootProject.Model.StatsStr;

@Service
public class ReportService implements Filter<Report, Object> {

	private static List<Report> reports = new ArrayList<>();
	private FilterService<Report> filter;
	private static List<StatsNum> statsNum = new ArrayList<>();
	private static List<StatsStr> statsStr = new ArrayList<>();


	public ReportService(FilterService<Report> filter) {
		this.filter = filter;
	}

	//legge dal file csv e costruisce gli oggetti report(utilizza JAR esterno)
	public void init() {
		String fileString = "csvfile.csv";
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(fileString));
			String[] lineStrings;
			int i = 0;
			//legge finchè ci sono dati
			while ((lineStrings = reader.readNext()) != null) {
				//sostituisce il carattere specificato con niente
				lineStrings[2] = lineStrings[2].replaceAll("\uFFFD", "");
				if (i != 0) {
					//chiama la funzione che genera gli oggetti report
					createReport(lineStrings, i, fileString);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Report> getReports() {
		return reports;
	}

	
	public List<Report> retrByLogicalFilter(String operator, String field1, String value1, String field2,
			String value2) {
		//chiama la funzione select della classe FilterService passando i parametri relativi al filtro corrente
		return (ArrayList<Report>) filter.select(this.getReports(), operator, field1, value1, field2, value2);
	}

	public List<Report> retrByConditionalFilter(String operator, String parametro, String valore) {
		//chiama la funzione selectCond della classe FilterService passando i parametri relativi al filtro corrente(condizionale)

		return (ArrayList<Report>) filter.condSelect(this.getReports(), operator, parametro, valore);
	}

		private static void createReport(String[] data, int i, String fileString) {
		//se la lunghezza degli attributi è pari a 7 li recupera
			if (data.length == 7) {
			String countryString = data[0];
			String refPeriodString = data[1];
			String itemString = data[2];
			String codeString = data[3];
			Double valueString = Double.parseDouble(data[4]);
			int extraction = Integer.parseInt(data[5]);
			int nca = Integer.parseInt(data[6]);
			//aggiunge alla List un oggetto di tipo report
			reports.add(
					new Report(countryString, refPeriodString, itemString, codeString, valueString, extraction, nca));
		} else {
			String querString = new String("c'è un dato non valido alla riga " + i + " del file " + fileString);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
	}

	public List<StatsNum> reportsStatsNum(String param) {
		int i = 0, n = 0;
		double somma = 0, min = 0, max = 0, dev = 0, sumdev = 0, dif = 0;
		double avg = 0;
		String valString = new String("value");
		String extString = new String("extraction");
		String ncaString = new String("nca");
		//verifica che esistano i report
		if (!reports.isEmpty()) {
			//se il parametro passato corrisponde a value
			if (param.equals(valString)) {
				//scorre i report
				for (Report report : reports) {
					//calcola le statistiche
					somma += report.getValue();
					if (n == 0)
						max = min = report.getValue();
					else if (report.getValue() < min)
						min = report.getValue();
					else if (report.getValue() > max)
						max = report.getValue();
					n++;
				} 
				//calcola la media
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
				String querString = new String(param + " non è un parametro valido...");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
			}
		} else {
			String querString = new String(
					"non sono stati creati gli oggetti richiesti, chiamare la rotta /localhost:8080/");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
		//calcolata la deviazione standard
		dev = sumdev / n;
		dev = Math.sqrt(dev);
		avg = tronca(avg);
		somma = tronca(somma);
		dev = tronca(dev);
		//aggiunge alla lista gli oggetti relativi alle statistiche 
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
		int count = 0, i = 0;
		boolean flag;
		String countryString = new String("country");
		String refPreiodString = new String("period");
		String itemString = new String("item");
		String codeString = new String("code");
		if (!reports.isEmpty()) {
			if (param.equals(countryString)) {
				for (Report report1 : reports) {
					count = 0;
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
					count = 0;
					flag = true;
					refString = report1.getRefperiod();
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
							String tempString = report2.getRefperiod();
							if (refString.equals(tempString))
								count++;
						}
						statsStr.add(new StatsStr(param, null, refString, null, null, count));
					}
				}
			} else if (param.equals(itemString)) {
				for (Report report1 : reports) {
					count = 0;
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
					count = 0;
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
				String querString = new String(param + " non è un parametro valido...");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
			}
		} else {
			String querString = new String(
					"non sono stati creati gli oggetti richiesti, chiamare la rotta /localhost:8080/");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
		return statsStr;
	}
}
