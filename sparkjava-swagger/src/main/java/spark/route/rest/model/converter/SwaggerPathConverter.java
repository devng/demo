/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package spark.route.rest.model.converter;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;
import java.util.Map;
import spark.route.rest.model.SwaggerPath;

public class SwaggerPathConverter implements Converter<Map<String, SwaggerPath>> {
	@Override
	public void serialize(Map<String, SwaggerPath> object, ObjectWriter writer, Context ctx) throws Exception {

	}

	@Override
	public Map<String, SwaggerPath> deserialize(ObjectReader reader, Context ctx) throws Exception {
		//ignore for now, we are only serializing to generate the swagger.json
		return null;
	}
}
