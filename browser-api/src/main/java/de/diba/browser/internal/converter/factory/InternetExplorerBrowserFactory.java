package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.Browsers;

public class InternetExplorerBrowserFactory implements BrowserFactory<InternetExplorerDriverService> {

	@Override
	public BrowserServiceConverter<InternetExplorerDriverService> getService() {
		return Browsers.Services.internetExplorer();
	}

	@Override
	public WebDriver create( final BrowserContext context, final InternetExplorerDriverService service ) {
		return new InternetExplorerDriver( service, toOptions( context ) );
	}

	@Override
	public WebDriver create( final BrowserContext context ) {
		return new InternetExplorerDriver( toOptions( context ) );
	}

	private static InternetExplorerOptions toOptions( final BrowserContext context ) {
		return new InternetExplorerOptions();
	}
}
