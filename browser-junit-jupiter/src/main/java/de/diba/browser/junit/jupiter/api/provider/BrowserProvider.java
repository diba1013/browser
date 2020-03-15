package de.diba.browser.junit.jupiter.api.provider;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;

public interface BrowserProvider {

	Stream<BrowserArgument> provide( ExtensionContext context );
}
