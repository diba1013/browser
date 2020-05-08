package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.Browsers;

public class EdgeBrowserFactory implements BrowserFactory<EdgeDriverService> {

	@Override
	public BrowserServiceConverter<EdgeDriverService> getService() {
		return Browsers.Services.edge();
	}

	@Override
	public WebDriver create( final BrowserContext context, final EdgeDriverService service ) {
		return new EdgeDriver( service, toOptions( context ) );
	}

	@Override
	public WebDriver create( final BrowserContext context ) {
		return new EdgeDriver( toOptions( context ) );
	}

	private static EdgeOptions toOptions( final BrowserContext context ) {
		return new EdgeOptions();
	}
}
