package de.diba.browser.internal.service.supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserServiceConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;

class OnlyBrowserServiceSupplierTest {

	BrowserServiceSupplier delegate;
	BrowserServiceConverter<DriverService> converter;

	@BeforeEach
	void setUp() throws Exception {
		delegate = mock( BrowserServiceSupplier.class );

		converter = mock( BrowserServiceConverter.class );
		when( converter.create() ).thenReturn( mock( DriverService.class ) );
	}

	@Test
	void get_should_create_service_if_in_set() throws Exception {
		final BrowserServiceSupplier cut = new OnlyBrowserServiceSupplier( Set.of( BrowserType.CHROME ), delegate );

		cut.get( BrowserType.CHROME, converter );

		verify( delegate ).get( BrowserType.CHROME, converter );
	}

	@ParameterizedTest
	@EnumSource( value = BrowserType.class, names = "CHROME", mode = EnumSource.Mode.EXCLUDE )
	void get_should_not_create_service_if_missing( final BrowserType browser ) throws Exception {
		final BrowserServiceSupplier cut = new OnlyBrowserServiceSupplier( Set.of( BrowserType.CHROME ), delegate );

		cut.get( browser, converter );

		verifyNoInteractions( delegate, converter );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void get_with_empty_set_should_never_create_service( final BrowserType type ) throws Exception {
		final BrowserServiceSupplier cut = new OnlyBrowserServiceSupplier( Set.of(), delegate );

		cut.get( type, converter );

		verify( delegate, never() ).get( any(), any() );
	}
}
