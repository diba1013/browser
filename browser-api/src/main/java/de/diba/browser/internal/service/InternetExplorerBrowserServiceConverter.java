package de.diba.browser.internal.service;

import org.openqa.selenium.ie.InternetExplorerDriverService;

import de.diba.browser.api.BrowserServiceConverter;

public class InternetExplorerBrowserServiceConverter implements BrowserServiceConverter<InternetExplorerDriverService> {

	@Override
	public InternetExplorerDriverService create() throws Exception {
		return InternetExplorerDriverService.createDefaultService();
	}
}
