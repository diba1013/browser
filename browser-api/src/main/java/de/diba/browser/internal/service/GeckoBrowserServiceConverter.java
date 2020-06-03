package de.diba.browser.internal.service;

import org.openqa.selenium.firefox.GeckoDriverService;

import de.diba.browser.api.BrowserServiceConverter;

public class GeckoBrowserServiceConverter implements BrowserServiceConverter<GeckoDriverService> {

	@Override
	public GeckoDriverService create() throws Exception {
		return GeckoDriverService.createDefaultService();
	}
}
