package de.diba.browser.internal.service.supplier;

import static de.diba.browser.api.Browsers.Services.Suppliers.all;
import static de.diba.browser.api.Browsers.Services.Suppliers.none;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;

class EitherBrowserServiceSupplierTest {

	BrowserServiceSupplier none;
	BrowserServiceSupplier all;

	BrowserServiceConverter<DriverService> converter;

	@BeforeEach
	void setUp() throws Exception {
		none = spy( none() );
		all = spy( all() );

		converter = mock( BrowserServiceConverter.class );
		when( converter.create() ).thenReturn( mock( DriverService.class ) );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void get_should_take_the_first_driver_service( final BrowserType type ) throws Exception {
		final BrowserServiceSupplier cut = new EitherBrowserServiceSupplier( List.of( none, all ) );

		assertThat( cut.get( type, converter ) ).isPresent();
		verify( none ).get( type, converter );
		verify( all ).get( type, converter );
		verify( converter ).create();
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void get_should_not_query_following_services_if_found( final BrowserType type ) throws Exception {
		final BrowserServiceSupplier cut = new EitherBrowserServiceSupplier( List.of( all, none ) );

		assertThat( cut.get( type, converter ) ).isPresent();
		verify( all ).get( type, converter );
		verifyNoInteractions( none );
		verify( converter ).create();
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void get_should_not_create_browser_if_no_supply( final BrowserType type ) throws Exception {
		final BrowserServiceSupplier cut = new EitherBrowserServiceSupplier( List.of( none ) );

		assertThat( cut.get( type, converter ) ).isEmpty();
		verify( none ).get( type, converter );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void get_with_empty_set_should_never_create_service( final BrowserType type ) throws Exception {
		final BrowserServiceSupplier cut = new EitherBrowserServiceSupplier( List.of() );

		assertThat( cut.get( type, converter ) ).isEmpty();
	}
}
