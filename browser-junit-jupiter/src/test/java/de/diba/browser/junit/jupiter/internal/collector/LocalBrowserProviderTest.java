package de.diba.browser.junit.jupiter.internal.collector;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.diba.browser.junit.jupiter.MockedBrowserProvider;
import de.diba.browser.junit.jupiter.MockedExtensionContext;
import de.diba.browser.junit.jupiter.MockedExtensionContext.ExtensionContextProvider;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.api.source.BrowserSource;

class LocalBrowserProviderTest {

	BrowserProvider cut;

	@BeforeEach
	void setUp() {
		cut = LocalBrowserProvider.of();
	}

	@Test
	void provide_should_be_empty_if_none_on_method() {
		final ExtensionContext context = MockedExtensionContext.of( NoneBrowserSource.class );

		assertThat( cut.provide( context ) ).isEmpty();
	}

	@Test
	void provide_should_find_meta_and_annotations_on_method_and_class() {
		final ExtensionContext context = MockedExtensionContext.of( MethodBrowserSource.class );

		assertThat( cut.provide( context ) ).hasSize( 3 );
	}

	@Test
	void provide_should_eliminate_duplicate_meta_and_annotations_on_method_and_class() {
		final ExtensionContext context = MockedExtensionContext.of( DuplicateMethodBrowserSource.class );

		assertThat( cut.provide( context ) ).hasSize( 1 );
	}

	@Retention( RetentionPolicy.RUNTIME )
	@Target( { ElementType.METHOD, ElementType.TYPE } )
	@BrowserSource( MockedBrowserProvider.Single.class )
	@Repeatable( WrappedBrowserSources.class )
	@interface WrappedBrowserSource {}

	@Retention( RetentionPolicy.RUNTIME )
	@Target( { ElementType.METHOD, ElementType.TYPE } )
	@interface WrappedBrowserSources {

		WrappedBrowserSource[] value();
	}

	static class NoneBrowserSource implements ExtensionContextProvider {

		@Override
		public void method() {}
	}

	@WrappedBrowserSource
	static class MethodBrowserSource implements ExtensionContextProvider {

		@Override
		@BrowserSource( MockedBrowserProvider.Double.class )
		public void method() {}
	}

	@WrappedBrowserSource
	@WrappedBrowserSource
	@BrowserSource( MockedBrowserProvider.Single.class )
	@BrowserSource( MockedBrowserProvider.Single.class )
	static class DuplicateMethodBrowserSource implements ExtensionContextProvider {

		@Override
		@WrappedBrowserSource
		@WrappedBrowserSource
		@BrowserSource( MockedBrowserProvider.Single.class )
		@BrowserSource( MockedBrowserProvider.Single.class )
		public void method() {}
	}
}
