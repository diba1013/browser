package de.diba.browser.junit.jupiter.api.source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.diba.browser.api.BrowserType;
import de.diba.browser.junit.jupiter.api.provider.ResolutionProvider;
import de.diba.browser.junit.jupiter.internal.provider.DefaultResolutionProvider;
import de.diba.browser.junit.jupiter.internal.source.SearchBrowserProvider;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD } )
@Repeatable( Browsers.class )
@BrowserSource( SearchBrowserProvider.class )
public @interface Browser {

	BrowserType[] value();

	Class<? extends ResolutionProvider> resolution() default DefaultResolutionProvider.class;

	boolean headless() default true;
}
