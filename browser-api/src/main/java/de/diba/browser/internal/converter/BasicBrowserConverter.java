package de.diba.browser.internal.converter;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import de.diba.browser.internal.converter.factory.BrowserFactory;
import de.diba.browser.internal.converter.factory.ChromeBrowserFactory;
import de.diba.browser.internal.converter.factory.EdgeBrowserFactory;
import de.diba.browser.internal.converter.factory.FirefoxBrowserFactory;
import de.diba.browser.internal.converter.factory.InternetExplorerBrowserFactory;
import de.diba.browser.internal.converter.factory.OperaBrowserFactory;
import de.diba.browser.internal.converter.factory.SafariBrowserFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor( access = AccessLevel.PACKAGE )
public final class BasicBrowserConverter implements BrowserConverter {

	private static final Map<BrowserType, BrowserFactory<?>> DEFAULT_FACTORIES = Map.of( //
			BrowserType.CHROME, new ChromeBrowserFactory(), //
			BrowserType.EDGE, new EdgeBrowserFactory(), //
			BrowserType.FIREFOX, new FirefoxBrowserFactory(), //
			BrowserType.INTERNET_EXPLORER, new InternetExplorerBrowserFactory(), //
			BrowserType.OPERA, new OperaBrowserFactory(), //
			BrowserType.SAFARI, new SafariBrowserFactory() //
	);

	private final Map<BrowserType, BrowserFactory<?>> factories;
	private final BrowserServiceSupplier service;

	public BasicBrowserConverter( final BrowserServiceSupplier service ) {
		this( DEFAULT_FACTORIES, service );
	}

	@Override
	public WebDriver convert( final BrowserContext context ) throws Exception {
		final BrowserFactory<?> factory = factories.get( context.getBrowser() );
		if ( factory != null ) {
			return create( factory, context );
		}
		throw new UnsupportedOperationException(
				"Convert to type of '" + context.getBrowser() + "' is not supported." );
	}

	private <T extends DriverService> WebDriver create( final BrowserFactory<T> factory, final BrowserContext context )
			throws Exception {
		return service.get( context.getBrowser(), factory.getService() ) //
				.map( service -> factory.create( context, service ) ) //
				.orElseGet( () -> factory.create( context ) );
	}
}
