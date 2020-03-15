package de.diba.browser.junit.jupiter;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.BrowserType;
import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MockedBrowserProvider {

	private static final BrowserConverter converter = new MockedBrowserConverter();

	public static class Single implements BrowserProvider {

		@Override
		public Stream<BrowserArgument> provide( final ExtensionContext context ) {
			return Stream.of( BrowserArgument.of( //
					BrowserContext.builder() //
							.browser( BrowserType.CHROME ) //
							.build(), //
					converter //
			) );
		}
	}

	public static class Double implements BrowserProvider {

		@Override
		public Stream<BrowserArgument> provide( final ExtensionContext context ) {
			return Stream.of( BrowserType.FIREFOX, BrowserType.EDGE ) //
					.map( type -> BrowserContext.builder().browser( type ).build() ) //
					.map( browser -> BrowserArgument.of( browser, converter ) );
		}
	}

}
