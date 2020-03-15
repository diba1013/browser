package de.diba.browser.junit.jupiter.internal.provider;

import java.util.stream.Stream;

import de.diba.browser.api.cookie.CookieContext;
import de.diba.browser.junit.jupiter.api.provider.CookieProvider;

public class NoCookieProvider implements CookieProvider {

	@Override
	public Stream<CookieContext> get() {
		return Stream.empty();
	}
}
