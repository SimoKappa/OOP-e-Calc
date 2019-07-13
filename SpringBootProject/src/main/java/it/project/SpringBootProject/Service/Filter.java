package it.project.SpringBootProject.Service;

import java.util.Collection;

public interface Filter<E,T> {
	abstract Collection<E> retrByLogicalFilter (String operator, String field1, String value1, String field2, String value2);
	abstract Collection<E> retrByConditionalFilter (String operator, String field, String value);
}