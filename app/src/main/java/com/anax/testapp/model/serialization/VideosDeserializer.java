package com.anax.testapp.model.serialization;

import com.anax.testapp.model.VideoPojo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author yuriiostrovskyi on 5/31/17.
 */

public class VideosDeserializer implements JsonDeserializer<VideoPojo[]> {

	@Override
	public VideoPojo[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		//get category from response body
		JsonArray categories = json.getAsJsonObject().getAsJsonArray("categories");

		//Get videos array from Category object
		JsonArray videos = categories.get(0).getAsJsonObject().getAsJsonArray("videos");

		return new Gson().fromJson(videos, VideoPojo[].class);
	}
}