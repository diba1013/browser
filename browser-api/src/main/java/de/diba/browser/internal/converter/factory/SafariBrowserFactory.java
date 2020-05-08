package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.Browsers;

public class SafariBrowserFactory implements BrowserFactory<SafariDriverService> {

	@Override
	public BrowserServiceConverter<SafariDriverService> getService() {
		return Browsers.Services.safari();
	}

	@Override
	public WebDriver create( final BrowserContext context, final SafariDriverService service ) {
		return new SafariDriver( service, toOptions( context ) );
	}

	@Override
	public WebDriver create( final BrowserContext context ) {
		return new SafariDriver( toOptions( context ) );
	}

	private static SafariOptions toOptions( final BrowserContext context ) {
		return new SafariOptions();
	}
}
