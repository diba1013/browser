package de.diba.browser.internal.service.supplier;

import java.util.Optional;

import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;

public class NoBrowserServiceSupplier implements BrowserServiceSupplier {

	@Override
	public <T extends DriverService> Optional<T> get( final BrowserType browser,
			final BrowserServiceConverter<T> converter ) {
		return Optional.empty();
	}
}
