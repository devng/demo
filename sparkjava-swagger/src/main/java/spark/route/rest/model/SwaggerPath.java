package spark.route.rest.model;

import java.util.List;
import java.util.Map;

public class SwaggerPath {

	private List<String> consumes;

	private List<String> produces;

	private List<SwaggerParameter> parameters;

	private Map<String, SwaggerResponse> responses;

	public List<String> getConsumes() {
		return consumes;
	}

	public void setConsumes(List<String> consumes) {
		this.consumes = consumes;
	}

	public List<String> getProduces() {
		return produces;
	}

	public void setProduces(List<String> produces) {
		this.produces = produces;
	}

	public List<SwaggerParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<SwaggerParameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, SwaggerResponse> getResponses() {
		return responses;
	}

	public void setResponses(Map<String, SwaggerResponse> responses) {
		this.responses = responses;
	}
}
