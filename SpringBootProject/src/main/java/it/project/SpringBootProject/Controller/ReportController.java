package it.project.SpringBootProject.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.project.SpringBootProject.Model.Metadata;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Model.StatsNum;
import it.project.SpringBootProject.Model.StatsStr;
import it.project.SpringBootProject.Service.MetadataService;
import it.project.SpringBootProject.Service.ReportService;

@RestController
public class ReportController {
	@Autowired
	ReportService reportservice;
	@Autowired
	MetadataService metadataservice;

	@RequestMapping(value = "/reports/all", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieveRep(){
		return new ResponseEntity<>(reportservice.retrieveallreports(), HttpStatus.OK);
	}

	@RequestMapping(value = "/reports/metadata", method = RequestMethod.GET)
	public ResponseEntity<Object> getMetadata(){
		return new ResponseEntity<>(metadataservice.getMetadata(), HttpStatus.OK);
	}

	@RequestMapping(value = "/reports/stats/num/{param}", method = RequestMethod.GET)

	public ResponseEntity<Object> reportStats(@PathVariable("param") String paramString) {
		return new ResponseEntity<>(reportservice.reportsStatsNum(paramString), HttpStatus.OK);
	}

	@RequestMapping(value = "/reports/stats/str/{param}", method = RequestMethod.GET)
	public List<StatsStr> reportStatsStr(@PathVariable("param") String paraString) {
		return reportservice.reportStatsStr(paraString);
	}
}
