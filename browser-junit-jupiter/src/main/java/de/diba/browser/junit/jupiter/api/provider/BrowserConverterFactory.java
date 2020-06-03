package de.diba.browser.junit.jupiter.api.provider;

import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserServiceSupplier;

public interface BrowserConverterFactory {

	BrowserConverter create( BrowserServiceSupplier service );
}
