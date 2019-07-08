package it.project.SpringBootProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"it.project.SpringBootProject.Controller","it.project.SpringBootProject.Service"})  //va a cercare il controller nel package corretto
@SpringBootApplication
public class SpringBootProjectApplication {

	public static void main(String[] args){
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}
}
