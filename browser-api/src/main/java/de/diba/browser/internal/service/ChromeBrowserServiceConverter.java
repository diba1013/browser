package de.diba.browser.internal.service;

import org.openqa.selenium.chrome.ChromeDriverService;

import de.diba.browser.api.BrowserServiceConverter;

public class ChromeBrowserServiceConverter implements BrowserServiceConverter<ChromeDriverService> {

	@Override
	public ChromeDriverService create() throws Exception {
		return ChromeDriverService.createDefaultService();
	}
}
