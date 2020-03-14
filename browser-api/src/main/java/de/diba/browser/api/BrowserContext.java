package de.diba.browser.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BrowserContext {

	@NonNull
	private final BrowserType browser;
	@Builder.Default
	private final boolean headless = false;
}
