package de.diba.browser.internal.converter.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserServiceConverter;

public interface BrowserFactory<D extends DriverService> {

	BrowserServiceConverter<D> getService();

	WebDriver create( BrowserContext context, D service );

	WebDriver create( BrowserContext context );
}
