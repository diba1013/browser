package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.Browsers;

public class ChromeBrowserFactory implements BrowserFactory<ChromeDriverService> {

	@Override
	public BrowserServiceConverter<ChromeDriverService> getService() {
		return Browsers.Services.chrome();
	}

	@Override
	public WebDriver create( final BrowserContext context, final ChromeDriverService service ) {
		return new ChromeDriver( service, toOptions( context ) );
	}

	@Override
	public WebDriver create( final BrowserContext context ) {
		return new ChromeDriver( toOptions( context ) );
	}

	private static ChromeOptions toOptions( final BrowserContext context ) {
		final ChromeOptions options = new ChromeOptions();
		options.setHeadless( context.isHeadless() );
		return options;
	}
}
