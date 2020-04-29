package de.diba.browser.junit.jupiter.internal;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BrowserStore {

	private static final String KEY = "browser.container.driver";

	public static void put( final ExtensionContext context, final WebDriver driver ) {
		final Store store = getStore( context );
		log.debug( "Storing driver '{}'.", driver );
		store.put( KEY, driver );
	}

	public static Optional<WebDriver> get( final ExtensionContext context ) {
		final Store store = getStore( context );
		return Optional.ofNullable( store.get( KEY, WebDriver.class ) );
	}

	public static WebDriver getRequired( final ExtensionContext context ) {
		return get( context ).orElseThrow();
	}

	public static boolean contains( final ExtensionContext context ) {
		return get( context ).isPresent();
	}

	public static void quit( final ExtensionContext context ) {
		final Store store = getStore( context );
		final WebDriver driver = store.remove( KEY, WebDriver.class );
		if ( driver != null ) {
			log.debug( "Quitting driver '{}'.", driver );
			driver.quit();
		}
	}

	private static Store getStore( final ExtensionContext context ) {
		return context.getStore( create( context ) );
	}

	private static Namespace create( final ExtensionContext context ) {
		return Namespace.create( BrowserStore.class, context.getRequiredTestClass(), context.getRequiredTestMethod() );
	}
}
