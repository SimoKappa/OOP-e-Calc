package it.project.SpringBootProject.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ConnectionAdapter {
	
	private String result = "Si è verificato un errore";
	private String url = "";
	
	public ConnectionAdapter(String url)
	{
		this.url = url;
	}
	
	public void estabilishConnection()
	{
		File f = new File("./csvfile.csv");
		if(!f.exists())
		{
		try 
		{	
			//apre una connessione verso un url
			URLConnection newConnection = new URL(url).openConnection(); 
			//per leggere uno stream di byte
			InputStream in = newConnection.getInputStream();  
			 String data = "";
			 String line = "";
			 try {
			   //per trasformare lo stream di byte in caratteri
			   InputStreamReader isr = new InputStreamReader( in );
			   //legge da uno stream di caratteri
			   BufferedReader buf = new BufferedReader( isr );
			  
			   while ((line = buf.readLine()) != null) 
			   {
				   //in data ho l'intero JSON
				   data+= line;
			   }
			 } finally 
			 	{
				 in.close();
			 	}
			//vengono parsati i dati json e castati in un oggetto json (chiave-valore)
			JSONObject obj1 = (JSONObject) JSONValue.parseWithException(data); 
			//ritorna i valori associati alla chiave result
			JSONObject obj2 = (JSONObject) (obj1.get("result"));
			//ritorna i valori associati alla chiave resources
			JSONArray obj3 = (JSONArray) (obj2.get("resources"));
			
			for(Object o: obj3){
			    if (o instanceof JSONObject) {
			        JSONObject o1 = (JSONObject)o; 
			        //prendo il valore della chiave format
			        String format = (String)o1.get("format");
			        //prendo il valore della chiave url
			        String urlCSV = (String)o1.get("url");
			        //se il valore di format è quello che cerchiamo attiviamo la funzione download
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/CSV"))
			        {
			        	download(urlCSV, "csvfile.csv");
			        	result = "Download del file 'csvfile.csv' avvenuto correttamente, si troverà nella directory dell'applicazione.";
			        	break;
			        }
			    }
			}
		} 	//nel try interno possono essere lanciate eccezioni di parsing o di IO
			catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else 	
			{
				result = "Il file 'csvfile.csv è già presente nel tuo sistema, il download non è stato effettuato."; 
			}
	}
	
	@Override
	public String toString() {
		return "Stato del processo: " + result;
	}

	public static void download(String url, String nomefile) throws Exception 
	{
	    try (InputStream in = URI.create(url).toURL().openStream()) 
	    {
	    	//copia il file nel path target
	        Files.copy(in, Paths.get(nomefile));
	    }
	}
	
	
}
