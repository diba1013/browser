package de.diba.browser.api;

import java.util.Optional;

import org.openqa.selenium.remote.service.DriverService;

public interface BrowserServiceSupplier {

	<T extends DriverService> Optional<T> get( final BrowserType browser, BrowserServiceConverter<T> converter )
			throws Exception;
}
