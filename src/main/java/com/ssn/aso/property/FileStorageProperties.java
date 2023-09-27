package com.ssn.aso.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	private String uploadDir;

	public String getUploadDir() {
		if (uploadDir == null) {
			return uploadDir;
		}
		return uploadDir.replaceAll("\"", "");
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}
