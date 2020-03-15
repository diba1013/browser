package de.diba.browser.junit.jupiter;

import static org.mockito.Mockito.mock;

import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;

public class MockedBrowserConverter implements BrowserConverter {

	@Override
	public WebDriver convert( final BrowserContext context ) {
		return mock( WebDriver.class );
	}
}
