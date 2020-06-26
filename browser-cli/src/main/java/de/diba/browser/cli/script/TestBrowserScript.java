package de.diba.browser.cli.script;

import org.openqa.selenium.WebDriver;

import de.diba.browser.cli.api.BrowserScript;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBrowserScript implements BrowserScript {

	@Override
	public String getName() {
		return "browser:debug";
	}

	@Override
	public void execute( final WebDriver driver ) throws Exception {
		log.info( "Started browser {}.", driver );
	}
}
