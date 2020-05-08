package de.diba.browser.internal.service;

import org.openqa.selenium.opera.OperaDriverService;

import de.diba.browser.api.BrowserServiceConverter;

public class OperaBrowserServiceConverter implements BrowserServiceConverter<OperaDriverService> {

	@Override
	public OperaDriverService create() throws Exception {
		return OperaDriverService.createDefaultService();
	}
}
