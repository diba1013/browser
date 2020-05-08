package de.diba.browser.internal.service;

import org.openqa.selenium.edge.EdgeDriverService;

import de.diba.browser.api.BrowserServiceConverter;

public class EdgeBrowserServiceConverter implements BrowserServiceConverter<EdgeDriverService> {

	@Override
	public EdgeDriverService create() throws Exception {
		return EdgeDriverService.createDefaultService();
	}
}
