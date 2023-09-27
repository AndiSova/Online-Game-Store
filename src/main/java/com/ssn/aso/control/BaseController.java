package com.ssn.aso.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ssn.aso.property.ApiUrlProperties;
import com.ssn.aso.url.UrlUtil;

@RestController
public class BaseController {
	@Autowired
	private ApiUrlProperties apiUrlProperties;

	protected String getApiUrl() {
		String apiUrl = apiUrlProperties.getApiUrl();
		if (apiUrl == null || apiUrl.isEmpty()) {
			apiUrl = UrlUtil.getBaseEnvLinkURL();
		}
		return apiUrl;
	}
}
