package de.diba.browser.junit.jupiter.internal.service;

import java.util.Optional;

import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.Browsers;

public class DefaultBrowserServiceSupplier implements BrowserServiceSupplier {

	private final BrowserServiceSupplier delegate = Browsers.Services.Suppliers.all();

	@Override
	public <T extends DriverService> Optional<T> get( final BrowserType browser,
			final BrowserServiceConverter<T> converter ) throws Exception {
		return delegate.get( browser, converter );
	}
}
