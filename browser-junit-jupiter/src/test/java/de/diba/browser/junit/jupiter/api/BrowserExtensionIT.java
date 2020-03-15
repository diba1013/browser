package de.diba.browser.junit.jupiter.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import de.diba.browser.junit.jupiter.MockedBrowserProvider;
import de.diba.browser.junit.jupiter.api.source.BrowserSource;

class BrowserExtensionIT {

	@BrowserField
	WebDriver driver;

	@BeforeEach
	void setUp( final WebDriver driver ) {
		assertThat( this.driver ).isEqualTo( driver );
	}

	@AfterEach
	void tearDown( final WebDriver driver ) {
		assertThat( this.driver ).isEqualTo( driver );
	}

	@BrowserTest
	@BrowserSource( MockedBrowserProvider.Single.class )
	void field_should_not_be_null_once_method_is_called( final WebDriver driver ) {
		assertThat( this.driver ).isEqualTo( driver );
	}
}
