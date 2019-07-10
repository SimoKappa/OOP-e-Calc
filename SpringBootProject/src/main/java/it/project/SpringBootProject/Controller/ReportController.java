package it.project.SpringBootProject.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	  
	  @GetMapping("/reports/stats/num")
	  public List<StatsNum> reportsStats (@RequestParam(name="param", defaultValue="none")String param) {
		  return reportservice.reportsStatsNum(param);
	  }
	  
	  @GetMapping("/reports/stats/str")
	  public List<StatsStr> reportStatsStr (@RequestParam(name="param", defaultValue="none")String param) {
		  return reportservice.reportStatsStr(param);
	  }
}
