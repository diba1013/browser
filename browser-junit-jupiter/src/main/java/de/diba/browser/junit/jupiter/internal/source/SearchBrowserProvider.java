package de.diba.browser.junit.jupiter.internal.source;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.ReflectionSupport;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.resolution.Resolution;
import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.api.provider.ResolutionProvider;
import de.diba.browser.junit.jupiter.api.source.Browser;

public class SearchBrowserProvider implements BrowserProvider {

	@Override
	public Stream<BrowserArgument> provide( final ExtensionContext context ) {
		return findBrowsers( context ) //
				.flatMap( this::mapToArgument ) //
				.distinct() //
				.map( BrowserArgument::of );
	}

	private Stream<Browser> findBrowsers( final ExtensionContext context ) {
		return Stream.concat( //
				findBrowsersOn( context.getRequiredTestMethod() ), //
				findBrowsersOn( context.getRequiredTestClass() ) //
		);
	}

	private Stream<Browser> findBrowsersOn( final AnnotatedElement element ) {
		return AnnotationSupport.findRepeatableAnnotations( element, Browser.class ).stream();
	}

	private Stream<BrowserContext> mapToArgument( final Browser browser ) {
		final Set<BrowserType> value = Set.of( browser.value() );
		final Set<Resolution> resolutions = resolution( browser );
		final boolean headless = browser.headless();

		return value.stream() //
				.flatMap( type -> {
					return resolutions.stream() //
							.map( resolution -> BrowserContext.builder() //
									.browser( type ) //
									.resolution( resolution ) //
									.headless( headless ) //
									.build() );
				} );
	}

	private Set<Resolution> resolution( final Browser browser ) {
		final ResolutionProvider provider = ReflectionSupport.newInstance( browser.resolution() );
		return provider.get() //
				.collect( Collectors.toSet() );
	}
}
