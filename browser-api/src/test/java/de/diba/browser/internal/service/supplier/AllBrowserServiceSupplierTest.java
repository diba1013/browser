package de.diba.browser.internal.service.supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;

class AllBrowserServiceSupplierTest {

	BrowserServiceConverter<DriverService> converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = mock( BrowserServiceConverter.class );
		when( converter.create() ).thenReturn( mock( DriverService.class ) );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void get_with_empty_set_should_never_create_service( final BrowserType type ) throws Exception {
		final BrowserServiceSupplier cut = new AllBrowserServiceSupplier();

		assertThat( cut.get( type, converter ) ).isPresent();
		verify( converter ).create();
	}
}
