package de.diba.browser.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BrowserType {
	CHROME( "chrome" ),
	FIREFOX( "firefox" ),
	EDGE( "edge" ),
	SAFARI( "safari" ),
	OPERA( "opera" ),
	INTERNET_EXPLORER( "ie" );

	@Getter
	private final String name;
}
