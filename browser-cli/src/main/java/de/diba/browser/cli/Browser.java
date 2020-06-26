package de.diba.browser.cli;

import de.diba.browser.api.BrowserType;
import de.diba.browser.api.Browsers;
import de.diba.browser.api.resolution.Resolution;
import de.diba.browser.cli.api.BrowserScript;
import de.diba.browser.cli.internal.BrowserExecutor;
import de.diba.browser.cli.internal.script.BrowserScriptLoader;
import de.diba.browser.cli.internal.commands.Execute;
import de.diba.browser.cli.internal.converter.BrowserScriptTypeConverter;
import de.diba.browser.cli.internal.converter.BrowserTypeConverter;
import de.diba.browser.cli.internal.converter.ResolutionTypeConverter;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command( name = "browser", mixinStandardHelpOptions = true )
public class Browser {

	public static void main( final String[] args ) {
		final CommandLine line = new CommandLine( Browser.class );

		line.addSubcommand( new Execute( new BrowserExecutor( Browsers.Converters.create() ) ) );

		line.registerConverter( BrowserType.class, new BrowserTypeConverter() );
		line.registerConverter( Resolution.class, new ResolutionTypeConverter() );
		line.registerConverter( BrowserScript.class, new BrowserScriptTypeConverter( new BrowserScriptLoader() ) );

		line.execute( args );
	}
}
