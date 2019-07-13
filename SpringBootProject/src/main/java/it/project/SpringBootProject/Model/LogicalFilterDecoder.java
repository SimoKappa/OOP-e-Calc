package it.project.SpringBootProject.Model;

import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;

public class LogicalFilterDecoder implements Decoder {
	private JSONObject filter;
	
	public LogicalFilterDecoder(JSONObject filter)
	{
		super();
		this.filter = filter;
	}
	
	@Override
	public String[] appliedFilter ()
	{
		String parametro1="";
		String valore1="";
		String parametro2="";
		String valore2="";
		String operatore="";
		Set<Map.Entry<String, JsonValue>> kvpairs = filter.entrySet();
		for (Map.Entry<String, JsonValue> kvpair : kvpairs)
		{
			operatore = kvpair.getKey();
		}
		switch(operatore)
		{
		case "$or":
		{
			List<?> operands = (List<?>) filter.get("$or");
			Object param1 = operands.get(0);
			Object param2 = operands.get(1);
			Set<Map.Entry<String, String>>  hash1 = ((HashMap) param1).entrySet();
			Set<Map.Entry<String, String>>  hash2 = ((HashMap) param2).entrySet();
			for(Map.Entry<String, String> par : hash1)
			{
				parametro1 = par.getKey();
				valore1 = par.getValue();
			}
			for(Map.Entry<String, String> par : hash2)
			{
				parametro2 = par.getKey();
				valore2 = par.getValue();
			}
			String[] filtrocompleto = new String[] { "or", parametro1, valore1, parametro2, valore2};
			return filtrocompleto;
		}
		 case "$and":
		 {
			 	List<?> operands = (List<?>) filter.get("$and");
				Object param1 = operands.get(0);
				Object param2 = operands.get(1);
				Set<Map.Entry<String, String>>  hash1 = ((HashMap) param1).entrySet();
				Set<Map.Entry<String, String>>  hash2 = ((HashMap) param2).entrySet();
				for(Map.Entry<String, String> par : hash1)
				{
					parametro1 = par.getKey();
					valore1 = par.getValue();
				}
				for(Map.Entry<String, String> par : hash2)
				{
					parametro2 = par.getKey();
					valore2 = par.getValue();
				}
				String[] filtrocompleto = new String[] {"and", parametro1, valore1, parametro2, valore2};
				return filtrocompleto;
		 }
		 case "country" :
		 case "refPeriod":
		 case "item":
		 case "code":
		 case "value":
		 case "nca":
		 case "extraction":
		 {
		  String[] Error = new String[] {"Cond"};
		  return Error;
		 }
		 default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST,("Filtro logico errato o attributo del filtro condizionale errato."));
	}
}
}
