package de.diba.browser.junit.jupiter.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserType;
import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.api.source.BrowserSource;

class BrowserExtensionIT {

	@BrowserField
	WebDriver driver;

	@FindBy( id = "div" )
	WebElement div;

	@FindBy( id = "section" )
	WebElement section;

	@BeforeEach
	void setUp( final WebDriver driver ) {
		assertThat( this.driver ).isEqualTo( driver );

		assertThat( div ).isNotNull();
		assertThat( section ).isNotNull();
	}

	@AfterEach
	void tearDown( final WebDriver driver ) {
		assertThat( this.driver ).isEqualTo( driver );
	}

	@BrowserTest
	@BrowserSource( MockedBrowser.class )
	void field_should_not_be_null_once_method_is_called( final WebDriver driver ) {
		assertThat( this.driver ).isEqualTo( driver );
	}

	static class MockedBrowser implements BrowserProvider {

		@Override
		public Stream<BrowserArgument> provide( final ExtensionContext context ) {
			return Stream.of( mockBrowser() );
		}

		private BrowserArgument mockBrowser() {
			return BrowserArgument.of( //
					BrowserContext.builder() //
							.browser( BrowserType.CHROME ) //
							.build(), //
					this::convert );
		}

		private WebDriver convert( final BrowserContext context ) {
			final WebDriver driver = mock( WebDriver.class );
			when( driver.findElement( any() ) ).thenReturn( mock( WebElement.class ) );
			return driver;
		}
	}
}
