package it.project.SpringBootProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import it.project.SpringBootProject.Service.ConnectionAdapter;
import it.project.SpringBootProject.Service.ReportService;

@RestController
public class InitController {
	
	@Autowired ReportService reportservice;
	
	@RequestMapping() 
	@ResponseBody
	public String defaultMethod()
	{
		ConnectionAdapter ca = new ConnectionAdapter("http://data.europa.eu/euodp/data/api/3/action/package_show?id=eiopa-sq");
		ca.estabilishConnection();
		reportservice.init();
		return ca.toString();
	}
}