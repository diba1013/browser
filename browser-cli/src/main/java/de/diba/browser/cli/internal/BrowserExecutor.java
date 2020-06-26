package de.diba.browser.cli.internal;

import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.cli.api.BrowserScript;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrowserExecutor {

	private final BrowserConverter converter;

	public void execute( final BrowserContext context, final BrowserScript script ) throws Exception {
		final WebDriver driver = converter.convert( context );
		try {
			script.execute( driver );
		} finally {
			driver.quit();
		}
	}
}
