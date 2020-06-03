package de.diba.browser.junit.jupiter.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import de.diba.browser.api.BrowserServiceSupplier;
import de.diba.browser.api.BrowserType;
import de.diba.browser.junit.jupiter.internal.service.DefaultBrowserServiceSupplier;

@Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@ExtendWith( BrowserServiceExtension.class )
public @interface BrowserService {

	BrowserType[] browsers() default {};

	Class<? extends BrowserServiceSupplier> supplier() default DefaultBrowserServiceSupplier.class;
}
