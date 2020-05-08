package de.diba.browser.internal.service.supplier;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EitherBrowserServiceSupplier implements BrowserServiceSupplier {

	private final List<BrowserServiceSupplier> suppliers;

	@Override
	public <T extends DriverService> Optional<T> get( final BrowserType browser,
			final BrowserServiceConverter<T> converter ) throws Exception {
		for ( final BrowserServiceSupplier supplier : suppliers ) {
			final Optional<T> service = supplier.get( browser, converter );
			if ( service.isPresent() ) {
				return service;
			}
		}
		return Optional.empty();
	}
}
