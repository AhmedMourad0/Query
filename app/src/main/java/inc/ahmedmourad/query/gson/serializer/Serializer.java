package inc.ahmedmourad.query.gson.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.List;

import inc.ahmedmourad.query.elements.model.PersistableElement;
import inc.ahmedmourad.query.elements.model.QueryElement;

public class Serializer implements JsonSerializer<List<QueryElement>> {

	public static final String PROPERTY_ELEMENT_TYPE = "elementType";

	@Override
	public JsonElement serialize(List<QueryElement> src, Type typeOfSrc, JsonSerializationContext context) {

		if (src == null)
			return null;

		JsonArray array = new JsonArray();

		for (QueryElement element : src) {

			if (!(element instanceof PersistableElement))
				continue;

			final JsonElement jsonElement = context.serialize(element, element.getClass());

			jsonElement.getAsJsonObject().addProperty(PROPERTY_ELEMENT_TYPE, ((PersistableElement) element).getElementType());

			array.add(jsonElement);
		}

		return array;
	}
}
