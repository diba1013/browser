package de.diba.browser.junit.jupiter.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.ANNOTATION_TYPE, ElementType.FIELD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface BrowserField {}
