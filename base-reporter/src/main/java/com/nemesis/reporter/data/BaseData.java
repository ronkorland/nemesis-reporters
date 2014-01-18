package com.nemesis.reporter.data;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class BaseData {

	private String id;

	public BaseData() {
		setId(UUID.randomUUID().toString());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (StringUtils.isNotBlank(id)) {
			this.id = id;
		}
	}

}
