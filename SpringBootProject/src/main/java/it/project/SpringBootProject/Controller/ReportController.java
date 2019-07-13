package it.project.SpringBootProject.Controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.project.SpringBootProject.Model.ConditionalFilterDecoder;
import it.project.SpringBootProject.Model.LogicalFilterDecoder;
import it.project.SpringBootProject.Model.Metadata;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Model.Stats;
import it.project.SpringBootProject.Service.MetadataService;
import it.project.SpringBootProject.Service.ReportService;

@RestController
public class ReportController {
	@Autowired
	ReportService reportservice;
	@Autowired
	MetadataService metadataservice;

	@GetMapping("/reports/all")
	public List<Report> retrieverep() {
		return reportservice.retrieveallreports();
	}

	@GetMapping("/metadata")
	public List<Metadata> getmetadata() {
		return metadataservice.getMetadata();
	}

	@GetMapping("/stats")
	public List<Stats> reportsStats(@RequestParam(name = "param", defaultValue = "none") String param) {
		return reportservice.reportsStats(param);
	}

	@PostMapping("/reports/filtered")
	public List<Report> reportsFiltered(@RequestBody JSONObject filter) {
		LogicalFilterDecoder test = new LogicalFilterDecoder(filter);
		String[] filtro = test.appliedFilter();
		if (filtro[0].equals("or") || filtro[0].equals("and")) {
			return reportservice.retrByLogicalFilter(filtro[0], filtro[1], filtro[2], filtro[3], filtro[4]);
		} else {
			ConditionalFilterDecoder cfd = new ConditionalFilterDecoder(filter);
			String[] filtroC = cfd.appliedFilter();
			if (filtroC[0].equals("$lt") || filtroC[0].equals("$gt")) {
				return reportservice.retrByConditionalFilter(filtroC[0], filtroC[1], filtroC[2]);
			} else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, filtroC[0] + " non è un operatore corretto.");
		}
	}

}
