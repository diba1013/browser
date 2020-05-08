package de.diba.browser.junit.jupiter.internal.collector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.cookie.CookieContext;
import de.diba.browser.junit.jupiter.MockedExtensionContext;
import de.diba.browser.junit.jupiter.MockedExtensionContext.ExtensionContextProvider;
import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserConverterFactory;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;

class GlobalBrowserProviderTest {

	BrowserConverter converter;
	BrowserConverterFactory factory;

	@BeforeEach
	void setUp() {
		converter = mock( BrowserConverter.class );
		factory = mock( BrowserConverterFactory.class );
		when( factory.create( any() ) ).thenReturn( converter );
	}

	@Test
	void provide_should_reuse_converter_if_present() {
		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build(), converter ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( mock( BrowserConverterFactory.class ) ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.allSatisfy( argument -> {
					assertThat( argument.getConverter() ).hasValue( converter );
				} );
	}

	@Test
	void provide_should_use_parent_converter_if_child_converter_is_null() {
		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.allSatisfy( argument -> {
					assertThat( argument.getConverter() ).hasValue( converter );
				} );
	}

	@Test
	void provide_should_merge_cookies() {
		final CookieContext localCookie = CookieContext.builder() //
				.url( "https://google.de" ) //
				.cookie( mock( Cookie.class ) ) //
				.build();

		final CookieContext globalCookie = CookieContext.builder() //
				.url( "https://google.com" ) //
				.cookie( mock( Cookie.class ) ) //
				.build();

		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.cookie( localCookie ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.cookie( globalCookie ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.extracting( BrowserArgument::getContext ) //
				.flatExtracting( BrowserContext::getCookies ) //
				.containsExactly( localCookie, globalCookie );
	}

	@Test
	void provide_should_merge_cookies_from_same_url() {
		final CookieContext localCookie = CookieContext.builder() //
				.url( "https://google.com" ) //
				.cookie( mock( Cookie.class ) ) //
				.build();

		final CookieContext globalCookie = CookieContext.builder() //
				.url( "https://google.com" ) //
				.cookie( mock( Cookie.class ) ) //
				.build();

		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.cookie( localCookie ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.cookie( globalCookie ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.extracting( BrowserArgument::getContext ) //
				.allSatisfy( context -> {
					assertThat( context.getCookies() ) //
							.hasSize( 1 ) //
							.flatExtracting( CookieContext::getCookies ) //
							.hasSize( 2 );
				} );
	}

	@Test
	void provide_should_not_use_url_if_none_specified() {
		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.extracting( BrowserArgument::getContext ) //
				.extracting( BrowserContext::getUrl ) //
				.containsOnlyNulls();
	}

	@Test
	void provide_should_use_url_of_local_if_no_global() {
		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.url( "https://google.com" ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.extracting( BrowserArgument::getContext ) //
				.extracting( BrowserContext::getUrl ) //
				.containsExactly( "https://google.com" );
	}

	@Test
	void provide_should_use_url_of_global_if_no_local() {
		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.url( "https://google.com" ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 1 ) //
				.extracting( BrowserArgument::getContext ) //
				.extracting( BrowserContext::getUrl ) //
				.containsExactly( "https://google.com" );
	}

	@Test
	void provide_should_merge_urls_ignoring_duplicates() {
		final BrowserProvider provider = mock( BrowserProvider.class );
		when( provider.provide( any() ) ).thenReturn( Stream.of( BrowserArgument.of( BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.url( "https://google.de" ) //
				.build() ) ) );

		final BrowserProvider cut = GlobalBrowserProvider.builder() //
				.local( provider ) //
				.factory( factory ) //
				.url( "https://google.de" ) //
				.url( "https://google.com" ) //
				.build();

		assertThat( cut.provide( MockedExtensionContext.of( NoExtensionContextProvider.class ) ) ) //
				.hasSize( 2 ) //
				.extracting( BrowserArgument::getContext ) //
				.extracting( BrowserContext::getUrl ) //
				.containsExactlyInAnyOrder( "https://google.com", "https://google.de" );
	}

	static class NoExtensionContextProvider implements ExtensionContextProvider {

		@Override
		public void method() {}
	}
}
