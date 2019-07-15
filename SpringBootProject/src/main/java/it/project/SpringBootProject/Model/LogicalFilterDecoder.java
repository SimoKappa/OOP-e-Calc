package it.project.SpringBootProject.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * elabora il filtro passato nel body della richiesta
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public class LogicalFilterDecoder implements Decoder {
	private JSONObject filter;

	public LogicalFilterDecoder(JSONObject filter) {
		super();
		this.filter = filter;
	}

	/**
	 * mappa l'oggetto json per ottenere i valori specificati. Si analizzano, poi,
	 * diversi casi a seconda della forma dei filtri richiesti
	 */
	@Override
	public String[] appliedFilter() {
		String parametro1 = "";
		String valore1 = "";
		String parametro2 = "";
		String valore2 = "";
		String operatore = "";
		// Mappo l'oggetto JSON in un Map Entry per poterne ottenere i valori
		Set<Map.Entry<String, JsonValue>> kvpairs = filter.entrySet();
		for (Map.Entry<String, JsonValue> kvpair : kvpairs) {
			// prende la prima chiave dell'oggetto JSON
			operatore = kvpair.getKey();
		}
		switch (operatore) {
		case "$or": {
			// il valore della key associata al parametro è mappato in una lista generica
			List<?> operands = (List<?>) filter.get("$or");
			// ottengo i valori dei due campi presenti nell'array list
			Object param1 = operands.get(0);
			Object param2 = operands.get(1);
			// Mappo questi oggetti in una Map Entry per ottenerne i valori
			Set<Map.Entry<String, String>> hash1 = ((HashMap) param1).entrySet();
			Set<Map.Entry<String, String>> hash2 = ((HashMap) param2).entrySet();
			for (Map.Entry<String, String> par : hash1) {
				// parametro e valore del primo argomento del filtro
				parametro1 = par.getKey();
				valore1 = par.getValue();
			}
			for (Map.Entry<String, String> par : hash2) {
				// parametro e valore del secondo argomento del filtro
				parametro2 = par.getKey();
				valore2 = par.getValue();
			}
			// creo l'array con i valori del filtro
			String[] filtrocompleto = new String[] { "or", parametro1, valore1, parametro2, valore2 };
			return filtrocompleto;
		}
		case "$and": {
			List<?> operands = (List<?>) filter.get("$and");
			Object param1 = operands.get(0);
			Object param2 = operands.get(1);
			Set<Map.Entry<String, String>> hash1 = ((HashMap) param1).entrySet();
			Set<Map.Entry<String, String>> hash2 = ((HashMap) param2).entrySet();
			for (Map.Entry<String, String> par : hash1) {
				parametro1 = par.getKey();
				valore1 = par.getValue();
			}
			for (Map.Entry<String, String> par : hash2) {
				parametro2 = par.getKey();
				valore2 = par.getValue();
			}
			String[] filtrocompleto = new String[] { "and", parametro1, valore1, parametro2, valore2 };
			return filtrocompleto;
		}
		// racchiude i casi in cui si utilizza un operatore condizionale
		case "country":
		case "refPeriod":
		case "item":
		case "code":
		case "value":
		case "nca":
		case "extraction": {
			String[] Error = new String[] { "Cond" };
			return Error;
		}
		// gestione sintassi del filtro errata
		default:
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					("Filtro logico errato o attributo del filtro condizionale errato."));
		}
	}
}
