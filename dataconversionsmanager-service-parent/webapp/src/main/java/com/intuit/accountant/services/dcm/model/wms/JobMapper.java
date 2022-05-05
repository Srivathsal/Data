package com.intuit.accountant.services.dcm.model.wms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.accountant.services.dcm.model.JobDocument;
import com.intuit.accountant.services.dcm.model.JobPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class JobMapper {

	// TODO Inject ObjectMapper
	private ObjectMapper mapper = createObjectMapper();

	private Logger log = LoggerFactory.getLogger(getClass());

	@PostConstruct
	public void init() {
		mapper = createObjectMapper();
	}
	
	public String mapToJson(Job job) {
		try {
			return mapper.writeValueAsString(job);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			// TODO Throw service exception
			throw new RuntimeException(e);
		}
	}

	public Job mapToJob(String json) {
		try {
			return mapper.readValue(json, Job.class);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			// TODO Throw service exception
			throw new RuntimeException(e);
		}
	}
	
	public JobPayload mapToJobPayload(String json) {
		try {
			return mapper.readValue(json, JobPayload.class);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			// TODO Throw service exception
			throw new RuntimeException(e);
		}
	}
	
	public JobDocument mapToJobDocument(String json) {
		try {
			return mapper.readValue(json, JobDocument.class);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			// TODO Throw service exception
			throw new RuntimeException(e);
		}
	}

	private ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

}
