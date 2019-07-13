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

public class ConditionalFilterDecoder implements Decoder {
	private JSONObject filter;

	public ConditionalFilterDecoder(JSONObject filter) {
		super();
		this.filter = filter;
	}

	@Override
	public String[] appliedFilter() {
		String parametro = "";
		String operatore = "";
		String valore = "";
		Set<Map.Entry<String, JsonValue>> kvpairs = filter.entrySet();
		for (Map.Entry<String, JsonValue> pair : kvpairs) {
			parametro = pair.getKey();
		}
		try {
			LinkedHashMap<String, String> params = (LinkedHashMap<String, String>) filter.get(parametro);
			Set<Map.Entry<String, String>> hash = ((HashMap) params).entrySet();
			for (Entry<String, String> val : hash) {
				operatore = val.getKey();
				valore = val.getValue();
			}
			String[] filtri = new String[] { operatore, parametro, valore };
			return filtri;
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sintassi del filtro errata");
		}
	}
}
