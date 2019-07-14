package it.project.SpringBootProject.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FilterService<T> {

	public static boolean check(Object value, String searched) {
		//controlla che il valore da cercare e del filtro siano numerici
		if (value instanceof Number && Double.valueOf(searched) instanceof Number) {
			Double valueC = ((Number) value).doubleValue();
			String searchedC = (String) searched;
			double prova = Double.valueOf(valueC);
			double prova1 = Double.valueOf(searchedC);
			return prova == prova1;
			//controlla che il valore da cercare e del filtro siano stringhe
		} else if (value instanceof String && searched instanceof String) {
			return searched.equals(value);
		}
		return false;
	}

	public Collection<T> condSelect(Collection<T> reports, String operator, String parametro, String valore) {
		Collection<T> filtrati = new ArrayList<>();
		for (T item : reports) {
			try {
				Method m = item.getClass()
						.getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1), null);
				try {
					Object tmp = m.invoke(item);
					String attuale = "";
					double attualeC;
					//metto in una stringa il valore cercato
					if (tmp instanceof Double) {
						attuale = Double.toString((double) tmp);
					} else if (tmp instanceof Integer) {
						attuale = Integer.toString((int) tmp);
					} else {
						return null;
					}
					//faccio il cast da una stringa a un double, cosi da avere tutti i numeri come double
					attualeC = Double.valueOf(attuale);
					if (operator.equals("$gt")) {
						if (attualeC > Double.valueOf(valore)) {
							filtrati.add(item);
						}
					} else if (operator.equals("$lt")) {
						if (attualeC < Double.valueOf(valore)) {
							filtrati.add(item);
						}
					} else
						return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Il valore inserito -> " + valore + ", non è in un formato valido.");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, parametro + " non è un attributo valido");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return filtrati;
	}

	public Collection<T> select(Collection<T> reports, String operator, String field1, String value1, String field2,
			String value2) {
		//array list da riempire con i valori che rispettano i filtri
		
		Collection<T> filtrati = new ArrayList<>();
		//scorro i reports
		for (T item : reports) {
			try {
				//si collega ai getter associati ai parametri passati
				Method m1 = item.getClass()
						.getMethod("get" + field1.substring(0, 1).toUpperCase() + field1.substring(1), null);
				Method m2 = item.getClass()
						.getMethod("get" + field2.substring(0, 1).toUpperCase() + field2.substring(1), null);
				try {
					//esegue i metodi di cui sopra
					Object tmp1 = m1.invoke(item);
					Object tmp2 = m2.invoke(item);
					if (operator.equals("or")) {
						if (FilterService.check(tmp1, value1) || FilterService.check(tmp2, value2)) {
							filtrati.add(item);
						}
					} else if (operator.equals("and")) {
						if (FilterService.check(tmp1, value1) && FilterService.check(tmp2, value2)) {
							filtrati.add(item);
						}
					}

				} catch (NumberFormatException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Uno dei due valori inseriti non è valido");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Uno dei due attributi inseriti non è valido.");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return filtrati;
	}
}
