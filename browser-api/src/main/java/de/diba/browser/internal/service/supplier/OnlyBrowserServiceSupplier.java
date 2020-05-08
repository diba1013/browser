package de.diba.browser.internal.service.supplier;

import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnlyBrowserServiceSupplier implements BrowserServiceSupplier {

	private final Set<BrowserType> types;
	private final BrowserServiceSupplier delegate;

	@Override
	public <T extends DriverService> Optional<T> get( final BrowserType browser,
			final BrowserServiceConverter<T> converter ) throws Exception {
		return types.contains( browser ) ? delegate.get( browser, converter ) : Optional.empty();
	}
}
