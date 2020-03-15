package de.diba.browser.api.cookie;

import java.util.List;

import org.openqa.selenium.Cookie;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class CookieContext {

	@NonNull
	private final String url;
	@Singular( "cookie" )
	private final List<Cookie> cookies;
}
