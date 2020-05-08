package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.Browsers;

public class FirefoxBrowserFactory implements BrowserFactory<GeckoDriverService> {

	@Override
	public BrowserServiceConverter<GeckoDriverService> getService() {
		return Browsers.Services.firefox();
	}

	@Override
	public WebDriver create( final BrowserContext context, final GeckoDriverService service ) {
		return new FirefoxDriver( service, toOptions( context ) );
	}

	@Override
	public WebDriver create( final BrowserContext context ) {
		return new FirefoxDriver( toOptions( context ) );
	}


	private static FirefoxOptions toOptions( final BrowserContext context ) {
		final FirefoxOptions options = new FirefoxOptions();
		options.setHeadless( context.isHeadless() );
		return options;
	}
}
