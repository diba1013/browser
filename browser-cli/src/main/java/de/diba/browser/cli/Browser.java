package de.diba.browser.cli;

import org.openqa.selenium.WebDriver;

import de.diba.browser.api.BrowserType;
import de.diba.browser.api.Browsers;
import de.diba.browser.api.resolution.Resolution;
import de.diba.browser.cli.api.BrowserScript;
import de.diba.browser.cli.internal.converter.BrowserScriptTypeConverter;
import de.diba.browser.cli.internal.converter.BrowserTypeConverter;
import de.diba.browser.cli.internal.converter.ResolutionTypeConverter;
import de.diba.browser.cli.internal.mixin.BrowserContextMixin;
import de.diba.browser.cli.internal.script.BrowserScriptLoader;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;

@Command( name = "browser", mixinStandardHelpOptions = true )
public class Browser {

	@Command( name = "execute", mixinStandardHelpOptions = true )
	public void execute( @Mixin final BrowserContextMixin context, @Parameters final BrowserScript script )
			throws Exception {
		final WebDriver driver = Browsers.create( context.convert() );
		try {
			script.execute( driver );
		} finally {
			driver.close();
		}
	}

	public static void main( final String[] args ) {
		final CommandLine line = new CommandLine( new Browser() );
		line.registerConverter( BrowserType.class, new BrowserTypeConverter() );
		line.registerConverter( Resolution.class, new ResolutionTypeConverter() );
		line.registerConverter( BrowserScript.class, new BrowserScriptTypeConverter( new BrowserScriptLoader() ) );
		line.execute( args );
	}
}
