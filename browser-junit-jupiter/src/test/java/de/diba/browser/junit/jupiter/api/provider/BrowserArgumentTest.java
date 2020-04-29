package de.diba.browser.junit.jupiter.api.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserType;

class BrowserArgumentTest {

	BrowserContext context;

	@BeforeEach
	void setUp() {
		context = BrowserContext.builder() //
				.browser( BrowserType.CHROME ) //
				.build();
	}

	@Test
	void of_with_context_should_have_empty_converter() {
		final BrowserArgument cut = BrowserArgument.of( context );

		assertThat( cut.getContext() ).isEqualTo( context );
		assertThat( cut.getConverter() ).isEmpty();
	}

	@Test
	void of_with_context_and_converter_should_have_converter() {
		final BrowserConverter converter = mock( BrowserConverter.class );
		final BrowserArgument cut = BrowserArgument.of( context, converter );

		assertThat( cut.getContext() ).isEqualTo( context );
		assertThat( cut.getConverter() ).hasValue( converter );
	}

	@Test
	void createDriver_should_throw_if_no_converter_present() {
		final BrowserArgument cut = BrowserArgument.of( context );

		assertThatThrownBy( cut::createDriver ).isInstanceOf( IllegalStateException.class );
	}

	@Test
	void createDriver_should_invoke_method_on_converter_if_present() throws Exception {
		final WebDriver driver = mock( WebDriver.class );

		final BrowserConverter converter = mock( BrowserConverter.class );
		when( converter.convert( context ) ).thenReturn( driver );

		final BrowserArgument cut = BrowserArgument.of( context, converter );

		assertThat( cut.createDriver() ).isEqualTo( driver );
	}
}
