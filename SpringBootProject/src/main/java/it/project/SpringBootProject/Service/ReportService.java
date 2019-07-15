package it.project.SpringBootProject.Service;

import java.util.Collection;
import java.util.List;

import org.springframework.context.support.StaticApplicationContext;

import it.project.SpringBootProject.Model.StatsNum;

/**
 * interfaccia implementata da {@link ReportServiceImpl}
 * 
 * @author Danilo Tomassini e Simone Cappella
 *
 */
public interface ReportService<E, T> {
	abstract Collection<E> retrByLogicalFilter(String operator, String field1, String value1, String field2,
			String value2);

	abstract Collection<E> retrByConditionalFilter(String operator, String field, String value);

	public abstract void init();

	public abstract Collection<E> getReports();

	public abstract List<StatsNum> reportsStatsNum(String param);

	public abstract List<?> reportStatsStr(String param);
}