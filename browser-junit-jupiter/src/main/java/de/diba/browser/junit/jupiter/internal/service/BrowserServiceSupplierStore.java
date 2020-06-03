package de.diba.browser.junit.jupiter.internal.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.ReflectionSupport;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.Browsers;
import de.diba.browser.api.Browsers.Services.Suppliers;
import de.diba.browser.junit.jupiter.api.BrowserService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrowserServiceSupplierStore {

	private static final String BROWSER_PREFIX = "browser.container.service.";
	private static final String SUPPLIER_KEY = "browser.container.service.supplier";

	public static void store( final ExtensionContext context ) {
		final Store store = getStore( context );
		AnnotationSupport.findAnnotation( context.getTestClass(), BrowserService.class ) //
				.map( service -> create( service, store ) ) //
				.map( SynchronizedBrowserServiceSupplier::new )
				.ifPresent( supplier -> store.put( SUPPLIER_KEY, supplier ) );
	}

	public static BrowserServiceSupplier get( final ExtensionContext context ) {
		final Store store = getStore( context );
		return store.getOrDefault( SUPPLIER_KEY, BrowserServiceSupplier.class, Browsers.Services.Suppliers.none() );
	}

	private static BrowserServiceSupplier create( final BrowserService service, final Store store ) {
		final BrowserServiceSupplier delegate = ReflectionSupport.newInstance( service.supplier() );
		final Set<BrowserType> browsers = Set.of( service.browsers() );

		final BrowserServiceSupplier lookup = new LookupBrowserServiceSupplier( store );
		final BrowserServiceSupplier create = new CreateBrowserServiceSupplier( store, delegate );
		final BrowserServiceSupplier only = browsers.isEmpty() ? create : Suppliers.only( browsers, create );
		return Suppliers.either( List.of( lookup, only ) );
	}

	public static void quit( final ExtensionContext context ) {
		final Store store = getStore( context );
		AnnotationSupport.findAnnotation( context.getTestClass(), BrowserService.class ) //
				.map( BrowserService::browsers ) //
				.stream() //
				.flatMap( Stream::of ) //
				.distinct() //
				.map( browser -> get( store, browser ) ) //
				.filter( Objects::nonNull ) //
				.forEach( service -> {
					log.debug( "Shutting down browser service '{}'.", service );
					service.stop();
				} );
	}

	private static Store getStore( final ExtensionContext context ) {
		return context.getStore( create( context ) );
	}

	private static Namespace create( final ExtensionContext context ) {
		return Namespace.create( BrowserServiceSupplierStore.class, context.getRequiredTestClass() );
	}

	private static void put( final Store store, final BrowserType browser, final DriverService service ) {
		store.put( BROWSER_PREFIX + browser.getName(), service );
	}

	private static DriverService get( final Store store, final BrowserType browser ) {
		return store.get( BROWSER_PREFIX + browser.getName(), DriverService.class );
	}

	@RequiredArgsConstructor
	private static class SynchronizedBrowserServiceSupplier implements BrowserServiceSupplier {

		private final BrowserServiceSupplier supplier;

		@Override
		@Synchronized( "supplier" )
		public <T extends DriverService> Optional<T> get( final BrowserType browser,
				final BrowserServiceConverter<T> converter ) throws Exception {
			return supplier.get( browser, converter );
		}
	}

	@RequiredArgsConstructor
	private static class LookupBrowserServiceSupplier implements BrowserServiceSupplier {

		private final Store store;

		@Override
		public <T extends DriverService> Optional<T> get( final BrowserType browser,
				final BrowserServiceConverter<T> converter ) throws Exception {
			return Optional.ofNullable( (T) BrowserServiceSupplierStore.get( store, browser ) );
		}

	}

	@RequiredArgsConstructor
	private static class CreateBrowserServiceSupplier implements BrowserServiceSupplier {

		private final Store store;
		private final BrowserServiceSupplier delegate;

		@Override
		public <T extends DriverService> Optional<T> get( final BrowserType browser,
				final BrowserServiceConverter<T> converter ) throws Exception {
			final Optional<T> service = delegate.get( browser, converter );
			if ( service.isPresent() ) {
				final T unwrapped = service.get();
				log.debug( "Starting and storing browser service '{}' for '{}'.", unwrapped, browser );
				unwrapped.start();
				BrowserServiceSupplierStore.put( store, browser, unwrapped );
			}
			return service;
		}
	}
}
