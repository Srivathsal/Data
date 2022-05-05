package com.intuit.accountant.services.dcm.resources.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.intuit.accountant.services.common.providers.jackson.UTCDateTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JSONHelper {
	private ObjectMapper objectMapper = null;
	public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.registerModule(new UTCDateTimeModule());
			objectMapper.registerModule(new JodaModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);			
		}
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	/**
	 * Convert JSON string to an object of a specific
	 * type.
	 * @param json
	 * @param classType
	 * @return
	 */
	public <T> T fromJson(String json, Class<T> classType) {		
		try {
			T result = getObjectMapper().readValue(json, classType);
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Unable to convert json to object: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Convert a json array to a list
	 * @param json
	 * @param dataClass
	 * @return
	 */
	public <T> List<T> fromJsonArray(String json, Class<T> dataClass) {
		try {
			List<T> result = getObjectMapper().readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, dataClass));
			return result;
		} catch (IOException e) {
			T singleResult;
			try {
				singleResult = getObjectMapper().readValue(json, dataClass);
				List<T> smallList = new ArrayList<>();
				smallList.add(singleResult);
				return smallList;
			} catch (IOException e1) {
				throw new RuntimeException("Unable to convert json array to object: " + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Convert an object ot JSON
	 * @param object
	 * @return
	 */
	public String toJson(Object object, boolean prettyPrint) {
		try {
			if (prettyPrint)
				getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			else
				getObjectMapper().disable(SerializationFeature.INDENT_OUTPUT);
			String json = getObjectMapper().writeValueAsString(object);
			return json;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to convert object to JSON: " + e.getMessage(), e);
		}
	}
	
	public String toJson(Object object) {
		return toJson(object, false);
	}
}
