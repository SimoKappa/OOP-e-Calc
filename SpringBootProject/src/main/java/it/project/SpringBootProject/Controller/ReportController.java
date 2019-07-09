package it.project.SpringBootProject.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.project.SpringBootProject.Model.Metadata;
import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Model.Stats;
import it.project.SpringBootProject.Service.MetadataService;
import it.project.SpringBootProject.Service.ReportService;

@RestController
public class ReportController {
	 @Autowired ReportService reportservice;
	 @Autowired MetadataService metadataservice;

	  @GetMapping("/reports/all")
	  public List<Report> retrieverep()
	  {
		  return reportservice.retrieveallreports();
	  }

	  @GetMapping("/metadata")
	  public List<Metadata> getmetadata()
	  {
		  return metadataservice.getMetadata();
	  }
	  
	  @GetMapping("/stats")
	  public List<Stats> reportsStats (@RequestParam(name="param", defaultValue="value")String param) {
		  return reportservice.reportsStats(param);
	  }
}
