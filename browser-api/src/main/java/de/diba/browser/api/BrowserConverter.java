package de.diba.browser.api;

import org.openqa.selenium.WebDriver;

public interface BrowserConverter {

	WebDriver convert( final BrowserContext context ) throws Exception;
}
