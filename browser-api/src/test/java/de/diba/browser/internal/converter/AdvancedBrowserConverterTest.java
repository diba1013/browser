package de.diba.browser.internal.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserType;
import de.diba.selenium.browser.api.cookie.CookieContext;
import de.diba.browser.api.resolution.Resolution;

class AdvancedBrowserConverterTest {

	WebDriver driver;
	WebDriver.Window window;
	WebDriver.Options options;
	WebDriver.Navigation navigation;

	BrowserConverter cut;

	@BeforeEach
	void setUp() {
		navigation = mock( WebDriver.Navigation.class );

		window = mock( WebDriver.Window.class );

		options = mock( WebDriver.Options.class );
		when( options.window() ).thenReturn( window );

		driver = mock( WebDriver.class, RETURNS_MOCKS );
		when( driver.manage() ).thenReturn( options );
		when( driver.navigate() ).thenReturn( navigation );

		final BrowserConverter basic = mock( BrowserConverter.class );
		when( basic.convert( any() ) ).thenReturn( driver );

		cut = new AdvancedBrowserConverter( basic );
	}

	@Test
	void convert_should_properly_setup_window() {
		final BrowserContext context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.resolution( Resolution.of( 500, 500 ) ) //
				.build();

		cut.convert( context );

		verify( window ).setSize( new Dimension( 500, 500 ) );
	}

	@Test
	void convert_with_empty_cookie_context_should_not_navigate() {
		final CookieContext emptyContext = CookieContext.builder() //
				.url( "https://example.com" ) //
				.build();

		final BrowserContext context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.cookie( emptyContext ) //
				.build();

		cut.convert( context );

		verifyNoInteractions( navigation );
	}

	@Test
	void convert_should_navigate_and_set_cookies() {
		final Cookie cookie = mock( Cookie.class );

		final CookieContext cookieContext = CookieContext.builder() //
				.url( "https://google.com" ) //
				.cookie( cookie ) //
				.build();

		final BrowserContext context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.cookie( cookieContext ) //
				.build();

		cut.convert( context );

		verify( navigation ).to( "https://google.com" );
		verify( options ).addCookie( cookie );
	}

	@Test
	void convert_should_set_cookies_before_navigate_to_final_url() {
		final Cookie cookie = mock( Cookie.class );

		final CookieContext cookieContext = CookieContext.builder() //
				.url( "https://google.com" ) //
				.cookie( cookie ) //
				.build();

		final BrowserContext context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.cookie( cookieContext ) //
				.url( "https://google.de" ) //
				.build();

		cut.convert( context );

		verify( navigation ).to( "https://google.com" );
		verify( options ).addCookie( cookie );

		final ArgumentCaptor<String> lastUrl = ArgumentCaptor.forClass( String.class );
		verify( navigation, times( 2 ) ).to( lastUrl.capture() );

		assertThat( lastUrl.getValue() ).isEqualTo( "https://google.de" );
	}

	@Test
	void convert_with_url_should_set_url() throws Exception {
		final BrowserContext context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.url( "https://google.com" ) //
				.build();

		cut.convert( context );

		verify( navigation ).to( "https://google.com" );
	}

	@Test
	void convert_with_no_url_should_not_attempt_to_navigate() {
		final BrowserContext context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build();

		cut.convert( context );

		verifyNoInteractions( navigation );
	}
}

