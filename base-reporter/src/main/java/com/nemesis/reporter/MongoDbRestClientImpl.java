package com.nemesis.reporter;

import java.io.File;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.nemesis.reporter.data.SuiteData;
import com.nemesis.reporter.data.TestData;
import com.nemesis.reporter.data.UserData;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
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
			cfg.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			client = Client.create(cfg);
		} catch (Throwable e) {

		}
	}

	private WebResource getBaseResource() {
		return getClient().resource(ApiUrl.BASE_URL.getPropValue());
	}

	public String getToken() {
		UserData user = new UserData("api", "muVucuz7");
		GenericEntity<UserData> genericInput = new GenericEntity<UserData>(user) {
		};

		ClientResponse clientResponse = getBaseResource().path(ApiUrl.LOGIN_RESOURCE.getPropValue())
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, genericInput);
		String s = clientResponse.getEntity(String.class);
		JSONObject jo;
		try {
			jo = new JSONObject(s);
			String token = jo.getString("token");
			return token;
		} catch (JSONException e) {
			return null;
		}

	}

	public SuiteData createSuite(SuiteData suite, String token) {

		GenericType<SuiteData> outputGenericType = new GenericType<SuiteData>() {
		};
		GenericEntity<SuiteData> genericInput = new GenericEntity<SuiteData>(suite) {
		};

		Builder requestBuilder = getBaseResource().path(ApiUrl.SUITE_RESOURCE.getPropValue()).getRequestBuilder();
		requestBuilder.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE);

		if (StringUtils.isNotBlank(token)) {
			requestBuilder.header("X-Auth-Token", token);
		}

		ClientResponse response = requestBuilder.post(ClientResponse.class, genericInput);
		SuiteData returnSuite = response.getEntity(outputGenericType);
		return returnSuite;
	}

	public SuiteData updateSuite(SuiteData suite, String token) {
		GenericType<SuiteData> outputGenericType = new GenericType<SuiteData>() {
		};
		GenericEntity<SuiteData> genericInput = new GenericEntity<SuiteData>(suite) {
		};

		Builder requestBuilder = getBaseResource().path(ApiUrl.SUITE_RESOURCE.getPropValue()).getRequestBuilder();
		requestBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		if (StringUtils.isNotBlank(token)) {
			requestBuilder.header("X-Auth-Token", token);
		}

		ClientResponse response = requestBuilder.put(ClientResponse.class, genericInput);

		SuiteData returnSuite = response.getEntity(outputGenericType);
		return returnSuite;
	}

	public TestData createTest(TestData test, String token) {
		GenericType<TestData> outputGenericType = new GenericType<TestData>() {
		};
		GenericEntity<TestData> genericInput = new GenericEntity<TestData>(test) {
		};

		Builder requestBuilder = getBaseResource().path(ApiUrl.TEST_RESOURCE.getPropValue()).getRequestBuilder();
		requestBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		if (StringUtils.isNotBlank(token)) {
			requestBuilder.header("X-Auth-Token", token);
		}

		ClientResponse response = requestBuilder.post(ClientResponse.class, genericInput);

		TestData returnTest = response.getEntity(outputGenericType);
		return returnTest;
	}

	public TestData updateTest(TestData test, String token) {
		GenericType<TestData> outputGenericType = new GenericType<TestData>() {
		};
		GenericEntity<TestData> genericInput = new GenericEntity<TestData>(test) {
		};

		Builder requestBuilder = getBaseResource().path(ApiUrl.TEST_RESOURCE.getPropValue()).getRequestBuilder();
		requestBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		if (StringUtils.isNotBlank(token)) {
			requestBuilder.header("X-Auth-Token", token);
		}

		ClientResponse response = requestBuilder.put(ClientResponse.class, genericInput);

		TestData returnTest = response.getEntity(outputGenericType);
		return returnTest;
	}

	public String uploadTestAttach(String testId, File file, String token) {
		FileDataBodyPart filePart = new FileDataBodyPart("file", file);
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		formDataMultiPart.bodyPart(filePart);

		Builder requestBuilder = getBaseResource().path(ApiUrl.TEST_ATTACHMENT_RESOURCE.getPropValue()).path(testId)
				.getRequestBuilder();
		requestBuilder.type(MediaType.MULTIPART_FORM_DATA);

		if (StringUtils.isNotBlank(token)) {
			requestBuilder.header("X-Auth-Token", token);
		}

		ClientResponse response = requestBuilder.post(ClientResponse.class, formDataMultiPart);

		return response.getEntity(String.class);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public static void main(String[] args) {
		MongoDbRestClient client = new MongoDbRestClientImpl();
		String token = client.getToken();
		System.out.println(token);
	}
}
