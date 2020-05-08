package de.diba.browser.junit.jupiter.api.provider;

import java.util.stream.Stream;

import de.diba.browser.api.resolution.Resolution;

public interface ResolutionProvider {

	Stream<Resolution> get();
}
