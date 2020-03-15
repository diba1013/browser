package de.diba.browser.api;

import de.diba.browser.api.resolution.Resolution;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BrowserContext {

	@NonNull
	private final BrowserType browser;
	@NonNull
	@Builder.Default
	private final Resolution resolution = Resolution.of( 1280, 800 );
	@Builder.Default
	private final boolean headless = false;
}
