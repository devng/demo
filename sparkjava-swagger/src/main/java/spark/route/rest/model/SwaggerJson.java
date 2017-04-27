package spark.route.rest.model;

import java.util.Map;

public class SwaggerJson {

	public String getSwagger() {
		return swagger;
	}

	public void setSwagger(String swagger) {
		this.swagger = swagger;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Map<String, Map<String, SwaggerPath>> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, Map<String, SwaggerPath>> paths) {
		this.paths = paths;
	}

	private String swagger = "1.0";

	private String basePath;

	private Map<String, Map<String, SwaggerPath>> paths;

}
