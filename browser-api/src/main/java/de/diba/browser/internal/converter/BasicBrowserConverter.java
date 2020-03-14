package de.diba.browser.internal.converter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;

public final class BasicBrowserConverter implements BrowserConverter {

	@Override
	public WebDriver convert( final BrowserContext context ) {
		switch ( context.getBrowser() ) {
			case CHROME:
				return new ChromeDriver( toChromeOptions( context ) );
			case FIREFOX:
				return new FirefoxDriver( toFirefoxOptions( context ) );
			case EDGE:
				return new EdgeDriver( toEdgeOptions( context ) );
			case SAFARI:
				return new SafariDriver( toSafariOptions( context ) );
			case OPERA:
				return new OperaDriver( toOperaOptions( context ) );
			case INTERNET_EXPLORER:
				return new InternetExplorerDriver( toIEOptions( context ) );
		}
		throw new UnsupportedOperationException(
				"Convert to type of '" + context.getBrowser() + "' is not supported." );
	}

	private static ChromeOptions toChromeOptions( final BrowserContext context ) {
		final ChromeOptions options = new ChromeOptions();
		options.setHeadless( context.isHeadless() );
		return options;
	}

	private static FirefoxOptions toFirefoxOptions( final BrowserContext context ) {
		final FirefoxOptions options = new FirefoxOptions();
		options.setHeadless( context.isHeadless() );
		return options;
	}

	private static EdgeOptions toEdgeOptions( final BrowserContext context ) {
		return new EdgeOptions();
	}

	private static SafariOptions toSafariOptions( final BrowserContext context ) {
		return new SafariOptions();
	}

	private static OperaOptions toOperaOptions( final BrowserContext context ) {
		return new OperaOptions();
	}

	private static InternetExplorerOptions toIEOptions( final BrowserContext context ) {
		return new InternetExplorerOptions();
	}
}
