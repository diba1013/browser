package de.diba.browser.junit.jupiter.api.source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD } )
@Repeatable( BrowserSources.class )
public @interface BrowserSource {

	Class<? extends BrowserProvider> value();
}
