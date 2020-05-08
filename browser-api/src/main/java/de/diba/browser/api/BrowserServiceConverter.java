package de.diba.browser.api;

import org.openqa.selenium.remote.service.DriverService;

public interface BrowserServiceConverter<D extends DriverService> {

	D create() throws Exception;
}
