package de.diba.browser.cli.internal.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

import de.diba.browser.api.resolution.Resolution;
import picocli.CommandLine;

public class ResolutionTypeConverter implements CommandLine.ITypeConverter<Resolution> {

	private static final Pattern pattern = Pattern.compile( "(?<width>\\d+)x(?<height>\\d+)" );

	@Override
	public Resolution convert( final String value ) throws Exception {
		final Matcher matcher = pattern.matcher( value );
		if ( !matcher.matches() ) {
			throw new IllegalArgumentException(
					String.format( "Resolution '%s' must be of '<width>x<height>'.", value ) );
		}
		final String width = matcher.group( "width" );
		final String height = matcher.group( "height" );
		return Resolution.of( NumberUtils.toInt( width ), NumberUtils.toInt( height ) );
	}
}
