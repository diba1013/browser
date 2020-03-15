package de.diba.browser.junit.jupiter.api;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.util.Preconditions;

import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.internal.BrowserTestInvocationContext;
import de.diba.browser.junit.jupiter.internal.BrowserTestNameFormatter;
import de.diba.browser.junit.jupiter.internal.collector.GlobalBrowserProvider;
import de.diba.browser.junit.jupiter.internal.collector.LocalBrowserProvider;

public final class BrowserExtension implements TestTemplateInvocationContextProvider {

	private static final String MISSING_BROWSER_MESSAGE = "Configuration error: You must configure at least one browser with a @BrowserSource or @Browser";
	private static final String INVALID_METHOD_ANNOTATION = "Configuration error: Method '%s' must be annotated with @BrowserTest";

	private static final LocalBrowserProvider provider = LocalBrowserProvider.of();

	@Override
	public boolean supportsTestTemplate( final ExtensionContext context ) {
		return AnnotationSupport.isAnnotated( context.getTestMethod(), BrowserTest.class );
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
			final ExtensionContext context ) {
		final Method method = context.getRequiredTestMethod();
		final BrowserTest browser = getRequiredBrowserTest( method );

		// Provide a more meaningful exception if no browsers have been found
		final List<BrowserArgument> arguments = Preconditions
				.notEmpty( searchForBrowsers( browser, context ), MISSING_BROWSER_MESSAGE );

		final BrowserTestNameFormatter formatter = BrowserTestNameFormatter.builder() //
				.pattern( browser.name() ) //
				.methodName( method.getName() ) //
				.displayName( context.getDisplayName() ) //
				.build();

		return arguments.stream() //
				.map( argument -> BrowserTestInvocationContext.of( formatter, argument ) );
	}

	private BrowserTest getRequiredBrowserTest( final Method method ) {
		return AnnotationSupport.findAnnotation( method, BrowserTest.class ).orElseThrow( () -> {
			return new IllegalStateException( String.format( INVALID_METHOD_ANNOTATION, method ) );
		} );
	}

	private List<BrowserArgument> searchForBrowsers( final BrowserTest browser, final ExtensionContext context ) {
		final BrowserProvider provider = createBrowserProvider( browser );
		return provider.provide( context ) //
				.collect( Collectors.toList() );
	}

	private BrowserProvider createBrowserProvider( final BrowserTest browser ) {
		try {
			return GlobalBrowserProvider.of( provider, browser );
		} catch ( final Exception e ) {
			throw new IllegalStateException( "Failed to read browser data.", e );
		}
	}

}
