/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package spark.route.rest.model;

public class SwaggerResponse {
	private String description;

	private SwaggerSchema schema;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SwaggerSchema getSchema() {
		return schema;
	}

	public void setSchema(SwaggerSchema schema) {
		this.schema = schema;
	}
}
