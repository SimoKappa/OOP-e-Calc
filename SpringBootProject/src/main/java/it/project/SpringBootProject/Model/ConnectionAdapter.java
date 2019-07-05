package it.project.SpringBootProject.Model;

import java.io.BufferedReader;
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
	
	public String result = "Si è verificato un errore";
	private String url = "";
	
	public ConnectionAdapter(String url)
	{
		this.url = url;
	}
	
	public void estabilishConnection()
	{
		try 
		{	
			URLConnection newConnection = new URL(url).openConnection();
			InputStream in = newConnection.getInputStream();  //per leggere uno stream di byte
			
			 String data = "";
			 String line = "";
			 try {
			   InputStreamReader isr = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( isr );
			  
			   while ((line = buf.readLine()) != null) 
			   {
				   data+= line;
			   }
			 } finally 
			 	{
				 in.close();
			 	}
			JSONObject obj1 = (JSONObject) JSONValue.parseWithException(data); 
			JSONObject obj2 = (JSONObject) (obj1.get("result"));
			JSONArray obj3 = (JSONArray) (obj2.get("resources"));
			
			for(Object o: obj3){
			    if (o instanceof JSONObject) {
			        JSONObject o1 = (JSONObject)o; 
			        String format = (String)o1.get("format");
			        String urlD = (String)o1.get("url");
			        System.out.println(format + " | " + urlD);
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/CSV"))
			        {
			        	download(urlD, "csvfile.csv");
			        	result = "Download del file 'csvfile.csv' avvenuto correttamente, si troverà nella directory dell'applicazione.";
			        	break;
			        }
			    }
			}
			System.out.println( "OK" );
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
	        Files.copy(in, Paths.get(nomefile));
	    }
	}
	
	
}
