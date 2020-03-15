package de.diba.browser.junit.jupiter.internal.provider;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.diba.browser.junit.jupiter.api.provider.CookieProvider;

class NoCookieProviderTest {

	@Test
	void get_should_be_empty() {
		final CookieProvider cut = new NoCookieProvider();

		assertThat( cut.get() ).isEmpty();
	}
}
