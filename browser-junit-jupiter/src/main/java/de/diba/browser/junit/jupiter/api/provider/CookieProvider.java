package de.diba.browser.junit.jupiter.api.provider;

import java.util.stream.Stream;

import de.diba.browser.api.cookie.CookieContext;

public interface CookieProvider {

	Stream<CookieContext> get();
}
