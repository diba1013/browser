package de.diba.browser.junit.jupiter.internal.provider;

import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.Browsers;
import de.diba.browser.junit.jupiter.api.provider.BrowserConverterFactory;

public class DefaultBrowserConverterFactory implements BrowserConverterFactory {

	@Override
	public BrowserConverter create( final BrowserServiceSupplier service ) {
		return Browsers.Converters.create( service );
	}
}
