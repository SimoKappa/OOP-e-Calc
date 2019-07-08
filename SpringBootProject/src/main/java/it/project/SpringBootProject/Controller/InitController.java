package it.project.SpringBootProject.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import it.project.SpringBootProject.Service.ConnectionAdapter;

@RestController
public class InitController {
	
	@RequestMapping() 
	@ResponseBody
	public String defaultMethod()
	{
		ConnectionAdapter ca = new ConnectionAdapter("http://data.europa.eu/euodp/data/api/3/action/package_show?id=eiopa-sq");
		ca.estabilishConnection();
		return ca.toString();
	}
}