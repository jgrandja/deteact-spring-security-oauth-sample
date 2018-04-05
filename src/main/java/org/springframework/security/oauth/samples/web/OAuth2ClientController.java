/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth.samples.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Joe Grandja
 */
@Controller
public class OAuth2ClientController {

	@Value("${oauth2-resource1.base-uri}")
	private String resource1BaseUri;

	@Autowired
	@Qualifier("client1RestTemplate")
	private OAuth2RestTemplate client1RestTemplate;

	@RequestMapping("/resource1")
	@ResponseBody
	public String[] resource1() {
		String[] messages = client1RestTemplate.getForObject(resource1BaseUri, String[].class);
		return messages;
	}

	@RequestMapping("/callback")
	public String callback() {
		// Force the client to complete the flow - Authorization Response phase
		resource1();

		// Redirect so the 'state' and 'code' parameters are not carried forward
		return "redirect:/resource1";
	}
}