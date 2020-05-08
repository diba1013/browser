package de.diba.browser.junit.jupiter.internal.provider;

import java.util.stream.Stream;

import de.diba.browser.api.resolution.Resolution;
import de.diba.browser.junit.jupiter.api.provider.ResolutionProvider;

public class DefaultResolutionProvider implements ResolutionProvider {

	@Override
	public Stream<Resolution> get() {
		return Stream.of( //
				Resolution.of( 1280, 800 ) //
		);
	}
}
