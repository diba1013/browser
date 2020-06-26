package de.diba.browser.cli.api;

import org.openqa.selenium.WebDriver;

public interface BrowserScript {

	String getName();

	void execute( WebDriver driver ) throws Exception;
}
