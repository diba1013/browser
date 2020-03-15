package de.diba.browser.junit.jupiter.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.diba.browser.junit.jupiter.MockedExtensionContext;
import de.diba.browser.junit.jupiter.MockedExtensionContext.ExtensionContextProvider;
import de.diba.browser.junit.jupiter.api.BrowserField;

class FieldStoreTest {

	@Test
	void injectDriver_should_do_nothing_if_no_field_present() {
		final EmptyField emptyField = new EmptyField();
		final WebDriver driver = mock( WebDriver.class );

		assertThatCode( () -> {
			FieldStore.injectDriver( MockedExtensionContext.of( emptyField ), driver );
		} ).doesNotThrowAnyException();
	}

	@Test
	void injectDriver_should_find_field() {
		final WebDriverField field = new WebDriverField();
		final WebDriver driver = mock( WebDriver.class );

		FieldStore.injectDriver( MockedExtensionContext.of( field ), driver );

		assertThat( field.driver ).isEqualTo( driver );
	}

	@Test
	void injectDriver_should_set_all_drivers() {
		final SubWebDriverField field = new SubWebDriverField();
		final WebDriver driver = mock( WebDriver.class );

		FieldStore.injectDriver( MockedExtensionContext.of( field ), driver );

		assertThat( field.driver ).isEqualTo( driver );
		assertThat( field.parent ).isEqualTo( driver );
	}

	@Test
	void injectDriver_should_verify_that_driver_is_compatible() {
		final RemoteWebDriverField field = new RemoteWebDriverField();
		final WebDriver driver = mock( ChromeDriver.class );

		FieldStore.injectDriver( MockedExtensionContext.of( field ), driver );

		assertThat( field.driver ).isEqualTo( driver );
	}

	@Test
	void injectDriver_should_throw_if_driver_and_field_is_incompatible() {
		final RemoteWebDriverField incompatibleField = new RemoteWebDriverField();
		final WebDriver driver = mock( WebDriver.class );

		assertThatThrownBy( () -> FieldStore.injectDriver( MockedExtensionContext.of( incompatibleField ), driver ) ) //
				.isInstanceOf( UnsupportedOperationException.class );
	}

	@Test
	void injectDriver_should_throw_if_field_is_not_of_type_WebDriver() {
		final IncompatibleField incompatibleField = new IncompatibleField();
		final WebDriver driver = mock( WebDriver.class );

		assertThatThrownBy( () -> FieldStore.injectDriver( MockedExtensionContext.of( incompatibleField ), driver ) ) //
				.isInstanceOf( UnsupportedOperationException.class );

		assertThatThrownBy( () -> FieldStore.injectDriver( MockedExtensionContext.of( incompatibleField ), null ) ) //
				.isInstanceOf( UnsupportedOperationException.class );
	}

	@Test
	void injectDriver_should_clear_field_if_null() {
		final WebDriverField field = new WebDriverField();
		field.driver = mock( WebDriver.class );

		FieldStore.injectDriver( MockedExtensionContext.of( field ), null );

		assertThat( field.driver ).isNull();
	}

	static class EmptyField implements ExtensionContextProvider {

		@Override
		public void method() {}
	}

	static class WebDriverField implements ExtensionContextProvider {

		@BrowserField
		WebDriver driver;

		@Override
		public void method() {}
	}

	static class SubWebDriverField extends WebDriverField {

		@BrowserField
		WebDriver parent;
	}

	static class RemoteWebDriverField implements ExtensionContextProvider {

		@BrowserField
		RemoteWebDriver driver;

		@Override
		public void method() {}
	}

	static class IncompatibleField implements ExtensionContextProvider {

		@BrowserField
		int driver;

		@Override
		public void method() {}
	}
}
