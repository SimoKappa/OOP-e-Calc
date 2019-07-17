package it.project.SpringBootProject.Controller;

import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import it.project.SpringBootProject.Model.ConditionalFilterDecoder;
import it.project.SpringBootProject.Model.LogicalFilterDecoder;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Service.MetadataService;
import it.project.SpringBootProject.Service.ReportServiceImpl;

/**
 * controller che dirige le richieste a seconda delle rotte e dei verbi
 * utilizzati
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
@RestController
public class ReportController {
	@Autowired
	ReportServiceImpl reportservice;
	@Autowired
	MetadataService metadataservice;

	/**
	 * restituisce tutti gli oggetti del dataset
	 * 
	 * @return tutto il dataset
	 */
	@RequestMapping(value = "/reports/all", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveRep() {
		return new ResponseEntity<>(reportservice.getReports(), HttpStatus.OK);
	}

	/**
	 * restituisce i metadati relativi agli attributi
	 * 
	 * @return metadati
	 */
	@RequestMapping(value = "/reports/metadata", method = RequestMethod.GET)
	public ResponseEntity<Object> getMetadata() {
		return new ResponseEntity<>(metadataservice.getMetadata(), HttpStatus.OK);
	}

	/**
	 * restituisce le statistiche relative ai dati numerici
	 * 
	 * @param paramString attributo su cui sono richieste le statistiche
	 * @return statistiche numeriche
	 */
	@RequestMapping(value = "/reports/stats/num/{param}", method = RequestMethod.GET)
	public ResponseEntity<Object> reportStats(@PathVariable("param") String paramString) {
		return new ResponseEntity<>(reportservice.reportsStatsNum(paramString), HttpStatus.OK);
	}

	/**
	 * restituisce le statistiche relative agli attributi di tipo stringa
	 * 
	 * @param paraString attributo su cui si vogliono contare le occorrenze
	 * @return occorrenze dei singoli elementi
	 */
	@RequestMapping(value = "/reports/stats/str/{param}", method = RequestMethod.GET)
	public List<?> reportStatsStr(@PathVariable("param") String paraString) {
		return reportservice.reportStatsStr(paraString);
	}

	/**
	 * restituisce il dataset dopo l'applicazione dei filti
	 * 
	 * @param filter filtro specificato in formato JSON nel body della richiesta
	 * @return dataset filtrato
	 */
	@PostMapping("/reports/filtered")
	public List<Report> reportsFiltered(@RequestBody JSONObject filter) {
		LogicalFilterDecoder test = new LogicalFilterDecoder(filter);
		String[] filtro = test.appliedFilter();
		// controlla se siano nel caso di un filtro logico
		if (filtro[0].equals("or") || filtro[0].equals("and")) {
			// ritorna un sottoinsieme dei report
			return reportservice.retrByLogicalFilter(filtro[0], filtro[1], filtro[2], filtro[3], filtro[4]);
		} else {
			ConditionalFilterDecoder cfd = new ConditionalFilterDecoder(filter);
			String[] filtroC = cfd.appliedFilter();
			// filtri condizionali
			if (filtroC[0].equals("$lt") || filtroC[0].equals("$gt")) {
				return reportservice.retrByConditionalFilter(filtroC[0], filtroC[1], filtroC[2]);
			} else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, filtroC[0] + " non è un operatore corretto.");
		}
	}
	
	@RequestMapping(value = "/reports/stats/num/nat/jni/{param}", method = RequestMethod.GET)
	public ResponseEntity<Object> reportStatsJni(@PathVariable("param") String param) {
		return new ResponseEntity<>(reportservice.reportStatsJni(param), HttpStatus.OK);
	}
}
