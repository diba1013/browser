package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.Browsers;

public class OperaBrowserFactory implements BrowserFactory<OperaDriverService> {

	@Override
	public BrowserServiceConverter<OperaDriverService> getService() {
		return Browsers.Services.opera();
	}

	@Override
	public WebDriver create( final BrowserContext context, final OperaDriverService service ) {
		return new OperaDriver( service, toOptions( context ) );
	}

	@Override
	public WebDriver create( final BrowserContext context ) {
		return new OperaDriver( toOptions( context ) );
	}

	private static OperaOptions toOptions( final BrowserContext context ) {
		return new OperaOptions();
	}
}
