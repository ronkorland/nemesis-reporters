package com.nemesis.reporter;

import java.io.File;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.nemesis.reporter.data.SuiteData;
import com.nemesis.reporter.data.TestData;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class MongoDbRestClientImpl implements MongoDbRestClient {

	private Client client;

	public MongoDbRestClientImpl() {
		try {

			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			cfg.getClasses().add(MultiPartWriter.class);
			cfg.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
					Boolean.TRUE);
			client = Client.create(cfg);
		} catch (Throwable e) {

		}
	}

	private WebResource getBaseResource() {
		return getClient().resource(ApiUrl.BASE_URL.getPropValue());
	}

	public SuiteData createSuite(SuiteData suite) {
		GenericType<SuiteData> outputGenericType = new GenericType<SuiteData>() {
		};
		GenericEntity<SuiteData> genericInput = new GenericEntity<SuiteData>(
				suite) {
		};
		ClientResponse response = getBaseResource()
				.path(ApiUrl.SUITE_RESOURCE.getPropValue())
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, genericInput);

		SuiteData returnSuite = response.getEntity(outputGenericType);
		return returnSuite;
	}

	public SuiteData updateSuite(SuiteData suite) {
		GenericType<SuiteData> outputGenericType = new GenericType<SuiteData>() {
		};
		GenericEntity<SuiteData> genericInput = new GenericEntity<SuiteData>(
				suite) {
		};
		ClientResponse response = getBaseResource()
				.path(ApiUrl.SUITE_RESOURCE.getPropValue())
				.type(MediaType.APPLICATION_JSON_TYPE)
				.put(ClientResponse.class, genericInput);

		SuiteData returnSuite = response.getEntity(outputGenericType);
		return returnSuite;
	}

	public TestData createTest(TestData test) {
		GenericType<TestData> outputGenericType = new GenericType<TestData>() {
		};
		GenericEntity<TestData> genericInput = new GenericEntity<TestData>(test) {
		};
		ClientResponse response = getBaseResource()
				.path(ApiUrl.TEST_RESOURCE.getPropValue())
				.type(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, genericInput);

		TestData returnTest = response.getEntity(outputGenericType);
		return returnTest;
	}

	public TestData updateTest(TestData test) {
		GenericType<TestData> outputGenericType = new GenericType<TestData>() {
		};
		GenericEntity<TestData> genericInput = new GenericEntity<TestData>(test) {
		};
		ClientResponse response = getBaseResource()
				.path(ApiUrl.TEST_RESOURCE.getPropValue())
				.type(MediaType.APPLICATION_JSON_TYPE)
				.put(ClientResponse.class, genericInput);

		TestData returnTest = response.getEntity(outputGenericType);
		return returnTest;
	}

	public String uploadTestAttach(String testId, File file) {
		FileDataBodyPart filePart = new FileDataBodyPart("file", file);
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		formDataMultiPart.bodyPart(filePart);
		ClientResponse response = getBaseResource()
				.path(ApiUrl.TEST_ATTACHMENT_RESOURCE.getPropValue())
				.path(testId).type(MediaType.MULTIPART_FORM_DATA)
				.post(ClientResponse.class, formDataMultiPart);

		return response.getEntity(String.class);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
