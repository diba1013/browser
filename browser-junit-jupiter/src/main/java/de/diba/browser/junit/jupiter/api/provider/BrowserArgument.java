package de.diba.browser.junit.jupiter.api.provider;

import java.util.Optional;

import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor( access = AccessLevel.PACKAGE )
public class BrowserArgument {

	@NonNull
	private final BrowserContext context;
	private final BrowserConverter converter;

	public static BrowserArgument of( final BrowserContext context ) {
		return new BrowserArgument( context, null );
	}

	public static BrowserArgument of( final BrowserContext context, final BrowserConverter converter ) {
		return new BrowserArgument( context, converter );
	}

	public Optional<BrowserConverter> getConverter() {
		return Optional.ofNullable( converter );
	}

	public WebDriver createDriver() {
		if ( converter == null ) {
			throw new IllegalStateException( "Converter must not be null. Cannot create driver for " + context + "." );
		}
		return converter.convert( context );
	}
}
