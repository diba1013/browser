package de.diba.browser.api.resolution;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor( staticName = "of" )
public class Resolution {

	private final int width;
	private final int height;
}
