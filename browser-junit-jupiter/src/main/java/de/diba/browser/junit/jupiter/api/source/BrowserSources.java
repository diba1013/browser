package de.diba.browser.junit.jupiter.api.source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD } )
public @interface BrowserSources {

	BrowserSource[] value();
}
