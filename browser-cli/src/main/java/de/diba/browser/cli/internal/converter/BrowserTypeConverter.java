package de.diba.browser.cli.internal.converter;

import java.util.stream.Stream;

import de.diba.browser.api.BrowserType;
import picocli.CommandLine;

public class BrowserTypeConverter implements CommandLine.ITypeConverter<BrowserType> {

	@Override
	public BrowserType convert( final String value ) throws Exception {
		return Stream.of( BrowserType.values() ) //
				.filter( type -> type.getName().equals( value ) ) //
				.findFirst() //
				.orElseThrow( () -> new IllegalArgumentException(
						String.format( "Browser type '%s' is not supported.", value ) ) );
	}
}
