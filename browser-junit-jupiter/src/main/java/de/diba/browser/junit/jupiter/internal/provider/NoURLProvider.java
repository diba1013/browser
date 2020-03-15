package de.diba.browser.junit.jupiter.internal.provider;

import java.util.stream.Stream;

import de.diba.browser.junit.jupiter.api.provider.URLProvider;

public class NoURLProvider implements URLProvider {

	@Override
	public Stream<String> get() {
		return Stream.empty();
	}
}
