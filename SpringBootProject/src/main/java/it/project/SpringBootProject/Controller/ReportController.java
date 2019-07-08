package it.project.SpringBootProject.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.project.SpringBootProject.Model.Report;
import it.project.SpringBootProject.Service.ReportService;

@RestController
public class ReportController {
	 @Autowired ReportService reportservice;
	 
	  @GetMapping("/reports/all")
	  public List<Report> retrieverep()
	  {
		  return reportservice.retrieveallreports();
	  }

}
