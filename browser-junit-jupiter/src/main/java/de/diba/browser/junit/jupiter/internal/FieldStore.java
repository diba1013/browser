package de.diba.browser.junit.jupiter.internal;

import static org.junit.platform.commons.support.HierarchyTraversalMode.TOP_DOWN;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.util.ReflectionUtils;
import org.openqa.selenium.WebDriver;

import de.diba.browser.junit.jupiter.api.BrowserField;

public class FieldStore {

	public static void injectDriver( final ExtensionContext context, final WebDriver driver ) {
		injectDriver( context.getRequiredTestClass(), context.getRequiredTestInstance(), driver );
	}

	private static void injectDriver( final Class<?> clazz, final Object instance, final WebDriver driver ) {
		final List<Field> fields = findFields( clazz );
		for ( final Field field : fields ) {
			assertType( field, driver );
			set( field, instance, driver );
		}
	}

	private static List<Field> findFields( final Class<?> clazz ) {
		return AnnotationSupport
				.findAnnotatedFields( clazz, BrowserField.class, ReflectionUtils::isNotStatic, TOP_DOWN );
	}

	private static void assertType( final Field field, final WebDriver driver ) {
		final Class<?> type = field.getType();
		if ( !WebDriver.class.isAssignableFrom( type ) ) {
			throw new UnsupportedOperationException(
					String.format( "Only fields of type WebDriver may be annotated, but got '%s'.", field ) );
		}
		if ( driver == null ) {
			return;
		}
		if ( !type.isInstance( driver ) ) {
			throw new UnsupportedOperationException(
					String.format( "Cannot inject driver of type '%s' into field '%s'.", driver.getClass().getName(),
							field ) );
		}
	}

	private static void set( final Field field, final Object instance, final WebDriver driver ) {
		if ( field.trySetAccessible() ) {
			try {
				field.set( instance, driver );
			} catch ( final IllegalAccessException e ) {
				throw new UnsupportedOperationException(
						String.format( "Failed to inject field %s with %s.", field, driver ), e );
			}
		}
	}
}
