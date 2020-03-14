package de.diba.browser.api;

import org.openqa.selenium.WebDriver;

import de.diba.browser.internal.converter.BasicBrowserConverter;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@UtilityClass
@Accessors( fluent = true )
public class Browsers {

	@Getter( lazy = true )
	private static final BrowserConverter defaultConverter = new BasicBrowserConverter();

	public static WebDriver create( final BrowserContext context ) {
		return create( context, defaultConverter() );
	}

	public static WebDriver create( final BrowserContext context, final BrowserConverter converter ) {
		return converter.convert( context );
	}
}
