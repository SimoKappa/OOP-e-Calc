package it.project.SpringBootProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import it.project.SpringBootProject.Service.ReportServiceImpl;

@ComponentScan(basePackages={"it.project.SpringBootProject.Controller","it.project.SpringBootProject.Service"})  //va a cercare il controller nel package corretto
@SpringBootApplication
public class SpringBootProjectApplication {
	
	//@Autowired ReportServiceImpl reportservice;
	//native double valueStats();
	//native int statsInt();

	public static void main(String[] args){
		SpringApplication.run(SpringBootProjectApplication.class, args);
		
		;
	}
	
	/*static {
		System.loadLibrary("libreria");
	}*/
}
