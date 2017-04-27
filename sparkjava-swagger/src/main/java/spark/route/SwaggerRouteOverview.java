package spark.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import spark.route.rest.model.SwaggerJson;
import spark.route.rest.model.SwaggerParameter;
import spark.route.rest.model.SwaggerParameterLocation;
import spark.route.rest.model.SwaggerPath;
import spark.route.rest.model.SwaggerResponse;
import spark.route.rest.model.SwaggerSchema;

public class SwaggerRouteOverview extends RouteOverview {
	private static final Pattern PATH_PARAMETER_MATCHER = Pattern.compile("/:(.*?)(/|\\z)");
	private static final String PATH_PARAMETER_REPLACEMENT = "/{$1}$2";

	public static SwaggerJson getSwaggerJson() {
		final SwaggerJson swaggerJson = new SwaggerJson();
		swaggerJson.setBasePath("/");
		swaggerJson.setPaths(generateRestPaths(RouteOverview.routes));
		return swaggerJson;
	}

	private static Map<String, Map<String, SwaggerPath>> generateRestPaths(List<RouteEntry> routes) {
		final Map<String, Map<String, SwaggerPath>> pathsMap = new HashMap<>();
		routes.stream().filter(routeEntry -> !routeEntry.path.contains("swagger.json")).forEach(
				routeEntry -> getSwaggerPathMap(pathsMap, routeEntry));
		return pathsMap;
	}

	private static Map<String, SwaggerPath> getSwaggerPathMap(Map<String, Map<String, SwaggerPath>> pathsMap, RouteEntry routeEntry) {

		Map<String, SwaggerPath> pathMap = pathsMap.get(routeEntry.path);
		if (pathMap == null) {
			pathMap = new HashMap<>();
			pathsMap.put(formatPath(routeEntry.path), pathMap);
		}

		SwaggerPath swaggerPath = pathMap.get(routeEntry.httpMethod.name());
		if (swaggerPath == null) {
			swaggerPath = new SwaggerPath();
			swaggerPath.setConsumes(Arrays.asList("application/json"));
			swaggerPath.setProduces(Arrays.asList("application/json"));
			swaggerPath.setParameters(getParameters(routeEntry));
			swaggerPath.setResponses(getResponses(routeEntry));
			pathMap.put(routeEntry.httpMethod.name(), swaggerPath);
		}
		return pathMap;
	}

	private static Map<String,SwaggerResponse> getResponses(RouteEntry routeEntry) {
		final Map<String, SwaggerResponse> responses = new HashMap<>();
		responses.put("200", getSuccessResponse(routeEntry));
		return responses;
	}

	private static SwaggerResponse getSuccessResponse(RouteEntry routeEntry) {
		final SwaggerResponse response = new SwaggerResponse();
		final SwaggerSchema swaggerSchema = new SwaggerSchema();
		swaggerSchema.setType("object");
		response.setSchema(swaggerSchema);
		return response;
	}

	private static String formatPath(final String path) {
		return PATH_PARAMETER_MATCHER.matcher(path).replaceAll(PATH_PARAMETER_REPLACEMENT);
	}

	private static List<SwaggerParameter> getParameters(RouteEntry routeEntry) {
		final List<SwaggerParameter> parameterList = new ArrayList<>();
		parameterList.addAll(getPathParameters(routeEntry));
		parameterList.addAll(getBodyParameters(routeEntry));
		return parameterList;
	}

	private static List<SwaggerParameter> getBodyParameters(RouteEntry routeEntry) {
		final List<SwaggerParameter> parameterList = new ArrayList<>();
		if (routeEntry.httpMethod == HttpMethod.post || routeEntry.httpMethod == HttpMethod.put || routeEntry.httpMethod == HttpMethod.patch) {
			final SwaggerParameter parameter = new SwaggerParameter();
			parameter.setName("body");
			parameter.setIn(SwaggerParameterLocation.body);
			final SwaggerSchema schema = new SwaggerSchema();
			schema.setType("object");
			parameter.setSchema(schema);
			parameterList.add(parameter);
		}
		return parameterList;
	}

	private static List<SwaggerParameter> getPathParameters(RouteEntry routeEntry) {
		final List<String> pathComponents = Arrays.asList(routeEntry.path.split("/"));
		return pathComponents.stream().filter(pathComponent -> pathComponent.startsWith(":")).map(pathParameter -> {
			SwaggerParameter param = new SwaggerParameter();
			param.setName(pathParameter.substring(1));
			param.setIn(SwaggerParameterLocation.path);
			param.setRequired(true);
			return param;
		}).collect(Collectors.toList());
	}
}
