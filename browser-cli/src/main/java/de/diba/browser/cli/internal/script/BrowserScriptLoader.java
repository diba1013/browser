package de.diba.browser.cli.internal.script;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import de.diba.browser.cli.api.BrowserScript;

public class BrowserScriptLoader {

	public ServiceLoader<BrowserScript> loader = ServiceLoader.load( BrowserScript.class );

	public Optional<BrowserScript> find( final String name ) {
		return StreamSupport.stream( loader.spliterator(), false ) //
				.filter( script -> name.equals( script.getName() ) ) //
				.findFirst();
	}
}
