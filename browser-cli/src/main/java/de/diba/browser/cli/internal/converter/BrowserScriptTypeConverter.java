package de.diba.browser.cli.internal.converter;

import de.diba.browser.cli.api.BrowserScript;
import de.diba.browser.cli.internal.script.BrowserScriptLoader;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

@RequiredArgsConstructor
public class BrowserScriptTypeConverter implements CommandLine.ITypeConverter<BrowserScript> {

	private final BrowserScriptLoader loader;

	@Override
	public BrowserScript convert( final String value ) throws Exception {
		return loader.find( value ) //
				.orElseThrow( () -> new IllegalArgumentException(
						String.format( "Script '%s' could not be found.", value ) ) );
	}
}
