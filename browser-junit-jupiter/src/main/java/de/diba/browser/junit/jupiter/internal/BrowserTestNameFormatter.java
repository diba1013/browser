package de.diba.browser.junit.jupiter.internal;

import static de.diba.browser.junit.jupiter.api.BrowserTest.BROWSER_NAME_PLACEHOLDER;
import static de.diba.browser.junit.jupiter.api.BrowserTest.BROWSER_RESOLUTION_PLACEHOLDER;
import static de.diba.browser.junit.jupiter.api.BrowserTest.DISPLAY_NAME_PLACEHOLDER;
import static de.diba.browser.junit.jupiter.api.BrowserTest.METHOD_NAME_PLACEHOLDER;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.resolution.Resolution;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public final class BrowserTestNameFormatter {

	private final String pattern;
	private final String methodName;
	private final String displayName;

	public String format( final BrowserContext context ) {
		return pattern //
				.replace( BROWSER_NAME_PLACEHOLDER, name( context ) ) //
				.replace( BROWSER_RESOLUTION_PLACEHOLDER, resolution( context ) ) //
				.replace( METHOD_NAME_PLACEHOLDER, methodName ) //
				.replace( DISPLAY_NAME_PLACEHOLDER, displayName );
	}

	private String name( final BrowserContext context ) {
		final BrowserType type = context.getBrowser();
		return type.getName();
	}

	private String resolution( final BrowserContext context ) {
		final Resolution resolution = context.getResolution();
		return resolution.getWidth() + "x" + resolution.getHeight();
	}
}
