package de.diba.browser.internal.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import de.diba.browser.internal.converter.factory.BrowserFactory;

class BasicBrowserConverterTest {

	DriverService service;

	BrowserServiceSupplier all;
	BrowserServiceSupplier none;

	WebDriver driver;
	BrowserFactory<DriverService> factory;

	@BeforeEach
	void setUp() throws Exception {
		service = mock( DriverService.class );

		all = mock( BrowserServiceSupplier.class );
		when( all.get( any(), any() ) ).thenReturn( Optional.of( service ) );
		none = mock( BrowserServiceSupplier.class );
		when( none.get( any(), any() ) ).thenReturn( Optional.empty() );

		driver = mock( WebDriver.class );

		factory = mock( BrowserFactory.class );
		when( factory.create( any() ) ).thenReturn( driver );
		when( factory.create( any(), eq( service ) ) ).thenReturn( driver );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void create_should_throw_if_factory_not_present( final BrowserType type ) {
		final BrowserConverter cut = new BasicBrowserConverter( Map.of(), all );

		assertThatThrownBy( () -> cut.convert( context( type ) ) ).isInstanceOf( UnsupportedOperationException.class );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void create_should_respect_service_present( final BrowserType type ) throws Exception {
		final BrowserConverter cut = new BasicBrowserConverter( factories( factory ), all );

		assertThat( cut.convert( context( type ) ) ).isEqualTo( driver );

		verify( factory, never() ).create( any() );
		verify( factory ).create( any(), eq( service ) );
	}

	@ParameterizedTest
	@EnumSource( BrowserType.class )
	void create_should_fallback_to_factory_without_service_if_missing( final BrowserType type ) throws Exception {
		final BrowserConverter cut = new BasicBrowserConverter( factories( factory ), none );

		assertThat( cut.convert( context( type ) ) ).isEqualTo( driver );

		verify( factory ).create( any() );
		verify( factory, never() ).create( any(), eq( service ) );
	}

	private Map<BrowserType, BrowserFactory<?>> factories( final BrowserFactory<DriverService> factory ) {
		return Stream.of( BrowserType.values() ) //
				.collect( Collectors.toMap( Function.identity(), k -> factory ) );
	}

	private BrowserContext context( final BrowserType type ) {
		return BrowserContext.builder() //
				.browser( type ) //
				.build();
	}
}
