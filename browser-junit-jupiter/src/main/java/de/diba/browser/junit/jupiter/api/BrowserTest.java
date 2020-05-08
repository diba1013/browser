package de.diba.browser.junit.jupiter.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import de.diba.browser.junit.jupiter.api.provider.BrowserConverterFactory;
import de.diba.browser.junit.jupiter.api.provider.CookieProvider;
import de.diba.browser.junit.jupiter.api.provider.URLProvider;
import de.diba.browser.junit.jupiter.internal.provider.DefaultBrowserConverterFactory;
import de.diba.browser.junit.jupiter.internal.provider.NoCookieProvider;
import de.diba.browser.junit.jupiter.internal.provider.NoURLProvider;

@Target( { ElementType.ANNOTATION_TYPE, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@TestTemplate
@ExtendWith( BrowserExtension.class )
public @interface BrowserTest {

	String DISPLAY_NAME_PLACEHOLDER = "{display.name}";
	String METHOD_NAME_PLACEHOLDER = "{method.name}";

	String BROWSER_NAME_PLACEHOLDER = "{browser.name}";
	String BROWSER_RESOLUTION_PLACEHOLDER = "{browser.resolution}";

	String DEFAULT_DISPLAY_NAME = BROWSER_NAME_PLACEHOLDER + "/" + BROWSER_RESOLUTION_PLACEHOLDER + "/" + METHOD_NAME_PLACEHOLDER;

	String name() default DEFAULT_DISPLAY_NAME;

	Class<? extends BrowserConverterFactory> factory() default DefaultBrowserConverterFactory.class;

	Class<? extends URLProvider> url() default NoURLProvider.class;

	Class<? extends CookieProvider> cookies() default NoCookieProvider.class;
}
