package de.diba.browser.junit.jupiter.internal.collector;

import java.lang.reflect.AnnotatedElement;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.util.ReflectionUtils;

import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.api.source.BrowserSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor( staticName = "of" )
public class LocalBrowserProvider implements BrowserProvider {

	@Override
	public Stream<BrowserArgument> provide( final ExtensionContext context ) {
		return findProviders( context ) //
				.flatMap( provider -> provider.provide( context ) ) //
				.distinct();
	}

	private Stream<? extends BrowserProvider> findProviders( final ExtensionContext context ) {
		return findSources( context ) //
				.map( BrowserSource::value ) //
				.map( ReflectionUtils::newInstance );
	}

	private Stream<BrowserSource> findSources( final ExtensionContext context ) {
		return Stream.concat( // 
				findSourcesOn( context.getRequiredTestClass() ), //
				findSourcesOn( context.getRequiredTestMethod() ) //
		);
	}

	private Stream<BrowserSource> findSourcesOn( final AnnotatedElement type ) {
		return AnnotationSupport.findRepeatableAnnotations( type, BrowserSource.class ).stream();
	}
}
