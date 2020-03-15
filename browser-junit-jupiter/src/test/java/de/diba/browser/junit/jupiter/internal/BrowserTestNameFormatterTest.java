package de.diba.browser.junit.jupiter.internal;

import static de.diba.browser.junit.jupiter.api.BrowserTest.BROWSER_NAME_PLACEHOLDER;
import static de.diba.browser.junit.jupiter.api.BrowserTest.BROWSER_RESOLUTION_PLACEHOLDER;
import static de.diba.browser.junit.jupiter.api.BrowserTest.DEFAULT_DISPLAY_NAME;
import static de.diba.browser.junit.jupiter.api.BrowserTest.DISPLAY_NAME_PLACEHOLDER;
import static de.diba.browser.junit.jupiter.api.BrowserTest.METHOD_NAME_PLACEHOLDER;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.resolution.Resolution;

class BrowserTestNameFormatterTest {

	BrowserContext context;

	@BeforeEach
	void setUp() {
		context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.resolution( Resolution.of( 1920, 1080 ) ) //
				.url( "https://google.com" ) //
				.build();
	}

	@Test
	void format_should_replace_method_and_original_display_name( final TestInfo info ) {
		final String method = info.getTestMethod().map( Method::getName ).orElseThrow();
		final String display = info.getDisplayName();

		final BrowserTestNameFormatter cut = new BrowserTestNameFormatter(
				DISPLAY_NAME_PLACEHOLDER + "-" + METHOD_NAME_PLACEHOLDER, method, display );

		assertThat( cut.format( context ) ).isEqualTo( display + "-" + method );
	}

	@Test
	void format_should_inject_pretty_resolution() {
		final BrowserTestNameFormatter cut = new BrowserTestNameFormatter( BROWSER_RESOLUTION_PLACEHOLDER, "method",
				"display" );

		assertThat( cut.format( context ) ).isEqualTo( "1920x1080" );
	}

	@Test
	void format_should_inject_browser() {
		final BrowserTestNameFormatter cut = new BrowserTestNameFormatter( BROWSER_NAME_PLACEHOLDER, "method",
				"display" );

		assertThat( cut.format( context ) ).isEqualTo( BrowserType.CHROME.getName() );
	}

	@Test
	void format_should_keep_everything_but_placeholders() {
		final BrowserTestNameFormatter cut = new BrowserTestNameFormatter( DEFAULT_DISPLAY_NAME, "method", "display" );

		assertThat( cut.format( context ) ).isEqualTo( "chrome/1920x1080/method" );
	}
}
