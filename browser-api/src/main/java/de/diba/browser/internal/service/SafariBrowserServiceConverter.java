package de.diba.browser.internal.service;

import org.openqa.selenium.safari.SafariDriverService;

import de.diba.browser.api.BrowserServiceConverter;

public class SafariBrowserServiceConverter implements BrowserServiceConverter<SafariDriverService> {

	@Override
	public SafariDriverService create() throws Exception {
		return SafariDriverService.createDefaultService();
	}
}
