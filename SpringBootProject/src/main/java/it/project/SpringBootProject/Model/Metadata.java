package it.project.SpringBootProject.Model;

public class Metadata {
		private String alias;
		private String sourceField;
		private String type;
		
		public Metadata(String alias, String sourceField, String type)
		{
			this.alias = alias;
			this.sourceField = sourceField;
			this.type = type;
		}

		public String getAlias() {
			return alias;
		}

		public String getSourceField() {
			return sourceField;
		}

		public String getType() {
			return type;
		}
		
		
}
