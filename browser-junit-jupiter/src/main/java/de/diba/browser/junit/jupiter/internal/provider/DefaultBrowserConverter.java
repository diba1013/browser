package de.diba.browser.junit.jupiter.internal.provider;

import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.Browsers;

public class DefaultBrowserConverter implements BrowserConverter {

	@Override
	public WebDriver convert( final BrowserContext context ) {
		return Browsers.create( context );
	}
}
