package de.diba.browser.junit.jupiter.internal.source;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.diba.browser.api.BrowserType;
import de.diba.browser.junit.jupiter.MockedExtensionContext;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.api.source.Browser;

class SearchBrowserProviderTest {

	BrowserProvider cut;

	@BeforeEach
	void setUp() {
		cut = new SearchBrowserProvider();
	}

	@Test
	void provide_should_be_empty_if_none_on_method( final TestInfo info ) {
		final ExtensionContext context = MockedExtensionContext.of( info );

		assertThat( cut.provide( context ) ).isEmpty();
	}

	@Test
	void provide_should_find_class_annotations() {
		final ExtensionContext context = MockedExtensionContext.of( SingleOnClass.class );

		assertThat( cut.provide( context ) ).hasSize( 1 );
	}

	@Test
	void provide_should_find_class_and_method_annotations() {
		final ExtensionContext context = MockedExtensionContext.of( SingleOnClassAndMethod.class );

		assertThat( cut.provide( context ) ).hasSize( 2 );
	}

	@Test
	void provide_should_ignore_duplicates_despite_place_of_declaration() {
		final ExtensionContext context = MockedExtensionContext.of( SingleDuplicateOnClassAndMethod.class );

		assertThat( cut.provide( context ) ).hasSize( 1 );
	}

	@Test
	@Browser( BrowserType.CHROME )
	void provide_should_find_single_direct_sources_on_method( final TestInfo info ) {
		final ExtensionContext context = MockedExtensionContext.of( info );

		assertThat( cut.provide( context ) ).hasSize( 1 );
	}

	@Test
	@Browser( BrowserType.CHROME )
	@Browser( BrowserType.FIREFOX )
	void provide_should_find_multiple_direct_sources_on_method( final TestInfo info ) {
		final ExtensionContext context = MockedExtensionContext.of( info );

		assertThat( cut.provide( context ) ).hasSize( 2 );
	}

	@Test
	@Browser( BrowserType.CHROME )
	@Browser( BrowserType.CHROME )
	void provide_should_not_reuse_same_annotations( final TestInfo info ) {
		final ExtensionContext context = MockedExtensionContext.of( info );

		assertThat( cut.provide( context ) ).hasSize( 1 );
	}

	@Test
	@Browser( BrowserType.CHROME )
	@Browser( value = BrowserType.CHROME, headless = false )
	void provide_should_reuse_same_annotations_if_different_context( final TestInfo info ) {
		final ExtensionContext context = MockedExtensionContext.of( info );

		assertThat( cut.provide( context ) ).hasSize( 2 );
	}

	@Browser( BrowserType.CHROME )
	private static class SingleOnClass implements MockedExtensionContext.ExtensionContextProvider {

		@Override
		public void method() {}
	}

	@Browser( BrowserType.CHROME )
	private static class SingleOnClassAndMethod implements MockedExtensionContext.ExtensionContextProvider {

		@Override
		@Browser( BrowserType.FIREFOX )
		public void method() {}
	}

	@Browser( BrowserType.CHROME )
	private static class SingleDuplicateOnClassAndMethod implements MockedExtensionContext.ExtensionContextProvider {

		@Override
		@Browser( BrowserType.CHROME )
		public void method() {}
	}
}
