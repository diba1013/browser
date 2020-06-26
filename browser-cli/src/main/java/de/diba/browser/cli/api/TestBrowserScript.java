package de.diba.browser.cli.api;

import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBrowserScript implements BrowserScript {

	@Override
	public String getName() {
		return "browser:test";
	}

	@Override
	public void execute( final WebDriver driver ) throws Exception {
		log.info( "Started: '{}'.", driver );
	}
}
