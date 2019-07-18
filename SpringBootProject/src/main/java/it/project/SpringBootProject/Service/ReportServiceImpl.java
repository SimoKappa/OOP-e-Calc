package it.project.SpringBootProject.Service;

import java.awt.SystemColor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import au.com.bytecode.opencsv.CSVReader;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Model.StatsNum;
import it.project.SpringBootProject.Model.StatsStrCode;
import it.project.SpringBootProject.Model.StatsStrCountry;
import it.project.SpringBootProject.Model.StatsStrItem;
import it.project.SpringBootProject.Model.StatsStrPeriod;

/**
 * Classe che incorpora un {@link Report} e gestisce le varie modalità con cui
 * questi vengono riportati o le eventuali statistiche sui diversi tipi di
 * attributo dei record
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
@Service
public class ReportServiceImpl implements ReportService<Report, Object> {

	public native void nativeStats();

	private static List<Report> reports = new ArrayList<>();
	private FilterService<Report> filter;
	private static List<StatsNum> statsNum = new ArrayList<>();
	private static List<StatsStrCountry> statsStrCountry = new ArrayList<>();
	private static List<StatsStrPeriod> statsStrPeriod = new ArrayList<>();
	private static List<StatsStrItem> statsStrItem = new ArrayList<>();
	private static List<StatsStrCode> statsStrCode = new ArrayList<>();
	private int i = 0;

	public ReportServiceImpl(FilterService<Report> filter) {
		this.filter = filter;
	}

	public ReportServiceImpl() {

	}

	/**
	 * legge dal file csv e costruisce gli oggetti report <br>
	 * <strong>NOTA</strong> - utilizza JAR esterno
	 */
	public void init() {
		if (reports.isEmpty()) {
			String fileString = "csvfile.csv";
			CSVReader reader = null;
			try {
				reader = new CSVReader(new FileReader(fileString));
				String[] lineStrings;
				// legge finchè ci sono dati
				while ((lineStrings = reader.readNext()) != null) {
					// elimina il carattere specificato
					lineStrings[2] = lineStrings[2].replaceAll("\uFFFD", "");
					if (i != 0) {
						// chiama la funzione che genera gli oggetti report
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
	}

	/**
	 * restituisce tutti i report
	 * 
	 * @return i report
	 */
	public List<Report> getReports() {
		return reports;
	}

	public int getCount() {
		return i;
	}

	/**
	 * filtra i report secondo l'utilizzo di filtri logici, chiama la funzione
	 * select della classe FilterService passando i parametri
	 * 
	 * @return report filtrati
	 */
	public List<Report> retrByLogicalFilter(String operator, String field1, String value1, String field2,
			String value2) {
		return (ArrayList<Report>) filter.select(this.getReports(), operator, field1, value1, field2, value2);
	}

	/**
	 * filtra i report secondo l'utilizzo di filtri condizionali, chiama la funzione
	 * selectCond della classe FilterService passando i parametri
	 * 
	 * @return repot filtrati
	 */
	public List<Report> retrByConditionalFilter(String operator, String parametro, String valore) {
		return (ArrayList<Report>) filter.condSelect(this.getReports(), operator, parametro, valore);
	}

	/**
	 * crea la lista di oggetti report
	 * 
	 * @param data       array con i dati letti dal file csv
	 * @param i          numero della riga che si sta leggendo
	 * @param fileString nome del file che si sta leggendo
	 */
	private static void createReport(String[] data, int i, String fileString) {
		// se la lunghezza degli attributi è pari a 7 li recupera
		if (data.length == 7) {
			String countryString = data[0];
			String refPeriodString = data[1];
			String itemString = data[2];
			String codeString = data[3];
			Double valueString = Double.parseDouble(data[4]);
			int extraction = Integer.parseInt(data[5]);
			int nca = Integer.parseInt(data[6]);
			// aggiunge alla List un oggetto di tipo report
			reports.add(
					new Report(countryString, refPeriodString, itemString, codeString, valueString, extraction, nca));
		} else {
			String querString = new String("c'è un dato non valido alla riga " + i + " del file " + fileString);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
	}

	/**
	 * calcola le statistiche sui parametri numerici quali: <strong>media, valore
	 * minimo, valore massimo, somma, deviazione standard e conta degli
	 * elementi</strong>
	 * 
	 * @param param parametro che specifica l'attributo su cui calcolare le
	 *              statistiche
	 * @return lista con le statistiche numeriche
	 */
	public List<StatsNum> reportsStatsNum(String param) {
		int n = 0;
		double somma = 0, min = 0, max = 0, dev = 0, sumdev = 0, dif = 0;
		double avg = 0;
		String valString = new String("value");
		String extString = new String("extraction");
		String ncaString = new String("nca");
		// verifica che esistano i report
		if (!reports.isEmpty()) {
			statsNum.clear();
			// caso in cui si richiedono le statistiche per l'attributo value
			if (param.equals(valString)) {
				// scorre i report
				for (Report report : reports) {
					// calcola le statistiche
					somma += report.getValue();
					// il primo if assegna minimo e massimo presi dal primo valore, altrimenti
					// controlla se ce ne son odi maggiori o minori e li riassegna
					if (n == 0)
						max = min = report.getValue();
					else if (report.getValue() < min)
						min = report.getValue();
					else if (report.getValue() > max)
						max = report.getValue();
					n++;
				}
				// calcola la media
				avg = somma / n;
				// foreach per calcolare la somma delle differenze al quadrato al fine di
				// calcolare la deviazione standard
				for (Report report : reports) {
					dif = report.getValue() - avg;
					dif = Math.pow(dif, 2);
					sumdev += dif;
				}
				// caso in cui si richiedono la statistiche per l'attributo extraction
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
				// caso in cui si richiedono le statistiche per l'attrbibuto nca
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
				// gestione del caso di parametro errato
			} else {
				String querString = new String(param + " non è un parametro valido...");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
			}
			// gestione del caso in cui il dataset non sia stato ancora importato
		} else {
			String querString = new String(
					"il dataset non è stato ancora importato, chiamare la rotta /localhost:8080/");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
		// calcolata la deviazione standard
		dev = sumdev / n;
		dev = Math.sqrt(dev);
		avg = tronca(avg);
		somma = tronca(somma);
		dev = tronca(dev);
		// aggiunge alla lista gli oggetti relativi alle statistiche
		statsNum.add(new StatsNum(param, avg, min, max, somma, n, dev));
		return statsNum;
	}

	/**
	 * riduce il numero di elementi dopo la virgola
	 * 
	 * @param a valore da troncare
	 * @return valore troncato
	 */
	private double tronca(double a) {
		a *= 10000000;
		a = Math.floor(a);
		a /= 10000000;
		return a;
	}

	/**
	 * conta le occorrenze per ogni elemento di tipo stringa, costruendo una lista
	 * con gli oggetti che contengono relativo elemento e conteggio
	 * 
	 * @param param parametro che specifica l'attributo su cui calcolare le
	 *              occorrenze
	 * @return lsita dei relativi oggetti
	 */
	public List<?> reportStatsStr(String param) {
		String refString;
		int count = 0;
		boolean flag;
		String countryString = new String("country");
		String refPreiodString = new String("period");
		String itemString = new String("item");
		String codeString = new String("code");
		// si controlla che il dataset sia stato importato
		if (!reports.isEmpty()) {
			// richiesta delle statistiche sull'attributo country
			if (param.equals(countryString)) {
				// scorre i report impostando un contatore a zero, un flag a true, e assegnando
				// il valore di get country ad una stringa di riferimento
				for (Report report1 : reports) {
					count = 0;
					flag = true;
					refString = report1.getCountry();
					// for each sulle statistiche per le stringhe gia popolate per verificare se la
					// stringa di riferimento sul quale si stanno calcolando le statistiche è già
					// stata utilizzata, in tal caso si imposta il flag a false e si esce dal for
					for (StatsStrCountry str : statsStrCountry) {
						String tempString = str.getCountry();
						if (refString.equals(tempString)) {
							// count = 0;
							flag = false;
							break;
						}
					}
					// se non sono state calcolate le occorrenze per la stringa in questione il flag
					// sarà ancora true e si potrà procedere con il calcolo delle occorrenze
					if (flag == true) {
						for (Report report2 : reports) {
							String tempString = report2.getCountry();
							if (refString.equals(tempString))
								count++;
						}
						statsStrCountry.add(new StatsStrCountry(param, refString, count));
					}
				}
				return statsStrCountry;
				// caso in cui il parametro passato sia period
			} else if (param.equals(refPreiodString)) {
				for (Report report1 : reports) {
					count = 0;
					flag = true;
					refString = report1.getRefperiod();
					for (StatsStrPeriod str : statsStrPeriod) {
						String tempString = str.getPeriodo();
						if (refString.equals(tempString)) {
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
						statsStrPeriod.add(new StatsStrPeriod(param, refString, count));
					}
				}
				return statsStrPeriod;
				// caso in cui il parametro passato SimpleHessianServiceExporter item
			} else if (param.equals(itemString)) {
				for (Report report1 : reports) {
					count = 0;
					flag = true;
					refString = report1.getItem();
					for (StatsStrItem str : statsStrItem) {
						String tempString = str.getItem();
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
						statsStrItem.add(new StatsStrItem(param, refString, count));
					}
				}
				return statsStrItem;
				// caso in cui il parametro passato sia code
			} else if (param.equals(codeString)) {
				for (Report report1 : reports) {
					count = 0;
					flag = true;
					refString = report1.getCode();
					for (StatsStrCode str : statsStrCode) {
						String tempString = str.getCode();
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
						statsStrCode.add(new StatsStrCode(param, refString, count));
					}
				}
				return statsStrCode;
				// gestione del caso in cui il parametro passato non rientri tra gli attributi
				// dell'oggetto
			} else {
				String querString = new String(param + " non è un parametro valido...");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
			}
			// gestione del caso in cui il dataset non sia ancora stato importato
		} else {
			String querString = new String(
					"il dataset non è stato ancora importato, chiamare la rotta /localhost:8080/");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
	}

	public List<?> reportStatsNumJni(String param) {
		double valori[] = new double[i];
		double stats[] = new double[5];
		int j = 0;
		String valString = new String("value");
		String extString = new String("extraction");
		String ncaString = new String("nca");
		if (!reports.isEmpty()) {
			statsNum.clear();

			if (param.equals(valString)) {
				for (Report report : reports) {
					valori[j] = report.getValue();
					j++;
				}
			} else if (param.equals(extString)) {
				for (Report report : reports) {
					valori[j] = report.getExtraction();
					j++;
				}
			} else if (param.equals(ncaString)) {
				for (Report report : reports) {
					valori[j] = report.getNca();
					j++;
				}
			} else {
				String querString = new String(param + " non è un parametro valido...");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
			}
			// gestione del caso in cui il dataset non sia stato ancora importato
		} else {
			String querString = new String(
					"il dataset non è stato ancora importato, chiamare la rotta /localhost:8080/");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, querString);
		}
		NativeStats nativeStats = new NativeStats(valori, j); // crea l'oggetto della classe
		stats = nativeStats.statsDouble(j); // chiama la funzione che chiamerà il metodo nativo
		statsNum.add(new StatsNum(param + " estratto con jni", stats[0], stats[1], stats[2], stats[4], j, stats[3]));
		return statsNum;
	}
	
	public List<?> reportStatsStrJni(String param){
		String parole[] = new String[i];
		ArrayList<String> contate;
		ArrayList<Integer> occorrenze;
		int j = 0;
		try
		{
		for( Report report : reports) {
			parole[j] = (String) (report.getClass().getMethod("get" + param.substring(0, 1).toUpperCase() + param.substring(1), null)).invoke(report);
			j++;
		}
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} 
		NativeStats ns = new NativeStats();
		ns.nativeString(parole, j);
		int i = 0;
		for (String paroleArrayList : parole) {
			System.out.println(parole[i]);
			i++;
		}
		return null;
	}

}
