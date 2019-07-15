package it.project.SpringBootProject.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.project.SpringBootProject.Model.Metadata;

/**
 * serve per ottenere i metadati, quindi l'elenco degli attributi e relativo
 * tipo
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
@Service
public class MetadataService {
	private static List<Metadata> metadata = new ArrayList<>();

	/**
	 * costruisce la lista metadata con nome degli attributi, descrizione di questi e tipo del dato
	 * @return la lista dei metadati
	 */
	public List<Metadata> getMetadata() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("csvfile.csv"));
			try {
				String[] attribs = (reader.readLine()).split(",");
				metadata.add(new Metadata("country", attribs[0], "String"));
				metadata.add(new Metadata("refPeriod", attribs[1], "String"));
				metadata.add(new Metadata("item", attribs[2], "String"));
				metadata.add(new Metadata("code", attribs[3], "String"));
				metadata.add(new Metadata("value", attribs[4], "Double"));
				metadata.add(new Metadata("extraction", attribs[5], "Integer"));
				metadata.add(new Metadata("nca", attribs[6], "Integer"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return metadata;
	}

}
