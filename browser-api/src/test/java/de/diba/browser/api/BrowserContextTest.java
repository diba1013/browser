package de.diba.browser.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BrowserContextTest {

	@Test
	void build_with_no_arguments_should_throw_NPE() {
		assertThatThrownBy( () -> BrowserContext.builder().build() ) //
				.isInstanceOf( NullPointerException.class );
	}

	@Test
	void build_with_minimal_setup_should_have_default_values() {
		final BrowserContext cut = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build();

		assertThat( cut.getBrowser() ).isEqualTo( BrowserType.CHROME );
		assertThat( cut.getResolution().getWidth() ).isEqualTo( 1280 );
		assertThat( cut.getResolution().getHeight() ).isEqualTo( 800 );
		assertThat( cut.isHeadless() ).isFalse();
		assertThat( cut.getUrl() ).isNull();
	}
}
