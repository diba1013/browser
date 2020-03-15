package de.diba.browser.junit.jupiter.api.provider;

import java.util.stream.Stream;

public interface URLProvider {

	Stream<String> get();
}
