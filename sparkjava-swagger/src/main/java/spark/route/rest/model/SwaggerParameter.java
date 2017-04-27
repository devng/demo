package spark.route.rest.model;

public class SwaggerParameter {
	private String name;

	private SwaggerParameterLocation in;

	private Boolean required;

	private SwaggerSchema schema;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SwaggerParameterLocation getIn() {
		return in;
	}

	public void setIn(SwaggerParameterLocation in) {
		this.in = in;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setSchema(SwaggerSchema schema) {
		this.schema = schema;
	}

	public SwaggerSchema getSchema() {
		return schema;
	}
}
