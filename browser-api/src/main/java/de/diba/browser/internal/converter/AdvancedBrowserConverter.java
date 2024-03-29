package de.diba.browser.internal.converter;

import java.util.List;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.cookie.CookieContext;
import de.diba.browser.api.resolution.Resolution;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor( access = AccessLevel.PACKAGE )
public final class AdvancedBrowserConverter implements BrowserConverter {

	private final BrowserConverter basic;

	public AdvancedBrowserConverter( final BrowserServiceSupplier service ) {
		this( new BasicBrowserConverter( service ) );
	}

	@Override
	public WebDriver convert( final BrowserContext context ) throws Exception {
		final WebDriver driver = basic.convert( context );

		final Options options = driver.manage();
		setupWindow( options.window(), context.getResolution() );

		final Navigation navigation = driver.navigate();
		setCookies( navigation, options, context.getCookies() );
		navigateToURL( navigation, context.getUrl() );

		return driver;
	}

	private void setupWindow( final Window window, final Resolution resolution ) {
		window.setSize( new Dimension( resolution.getWidth(), resolution.getHeight() ) );
	}

	private void setCookies( final Navigation navigation, final Options options, final List<CookieContext> cookies ) {
		cookies.forEach( cookie -> setCookie( navigation, options, cookie ) );
	}

	private void setCookie( final Navigation navigation, final Options options, final CookieContext context ) {
		final String url = context.getUrl();
		final List<Cookie> cookies = context.getCookies();
		if ( !cookies.isEmpty() ) {
			navigateToURL( navigation, url );
			cookies.forEach( options::addCookie );
		}
	}

	private void navigateToURL( final Navigation navigation, final String url ) {
		if ( url != null ) {
			navigation.to( url );
		}
	}
}
