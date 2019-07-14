package it.project.SpringBootProject.Model;

import java.sql.Array;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * elabora il filtro passato nel body della richiesta
 * 
 * @author danilo
 *
 */
public class ConditionalFilterDecoder implements Decoder {
	private JSONObject filter;

	public ConditionalFilterDecoder(JSONObject filter) {
		super();
		this.filter = filter;
	}

	/**
	 * viene mappato il filtro passato in formato JSON nel body della richiesta e si
	 * accede a tali informazioni per utilizzarle come filtri
	 */
	@Override
	public String[] appliedFilter() {
		String parametro = "";
		String operatore = "";
		String valore = "";
		// mappa l'oggetto in un map entry
		Set<Map.Entry<String, JsonValue>> kvpairs = filter.entrySet();
		// accedo al valore della chiave
		for (Map.Entry<String, JsonValue> pair : kvpairs) {
			parametro = pair.getKey();
		}
		try {
			// accedo all'oggetto JSON che è valore della prima chiave
			LinkedHashMap<String, String> params = (LinkedHashMap<String, String>) filter.get(parametro);
			Set<Map.Entry<String, String>> hash = ((HashMap) params).entrySet();
			for (Entry<String, String> val : hash) {
				operatore = val.getKey();
				valore = val.getValue();
			}
			// ritorno un array con i valori del filtro condizionale
			String[] filtri = new String[] { operatore, parametro, valore };
			return filtri;
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sintassi del filtro errata");
		}
	}
}
