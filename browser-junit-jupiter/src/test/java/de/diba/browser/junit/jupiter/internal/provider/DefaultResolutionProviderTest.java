package de.diba.browser.junit.jupiter.internal.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import de.diba.browser.api.resolution.Resolution;
import de.diba.browser.junit.jupiter.api.provider.ResolutionProvider;

class DefaultResolutionProviderTest {

	@Test
	void cut_should_be_equal_to_default_resolution() {
		final ResolutionProvider cut = new DefaultResolutionProvider();

		final Stream<Resolution> resolutions = cut.get();

		assertThat( resolutions ) //
				.hasSize( 1 ) //
				.allSatisfy( resolution -> {
					assertThat( resolution.getWidth() ).isEqualTo( 1280 );
					assertThat( resolution.getHeight() ).isEqualTo( 800 );
				} );
	}
}
