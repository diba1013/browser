package de.diba.browser.internal.converter;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.resolution.Resolution;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor( access = AccessLevel.PACKAGE )
public final class AdvancedBrowserConverter implements BrowserConverter {

	private final BrowserConverter basic;

	public AdvancedBrowserConverter() {
		this( new BasicBrowserConverter() );
	}

	@Override
	public WebDriver convert( final BrowserContext context ) {
		final WebDriver driver = basic.convert( context );

		final Options options = driver.manage();
		setupWindow( options.window(), context.getResolution() );

		navigateToURL( driver.navigate(), context.getUrl() );

		return driver;
	}

	private void setupWindow( final Window window, final Resolution resolution ) {
		window.setSize( new Dimension( resolution.getWidth(), resolution.getHeight() ) );
	}

	private void navigateToURL( final Navigation navigation, final String url ) {
		if ( url != null ) {
			navigation.to( url );
		}
	}
}
