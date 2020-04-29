package de.diba.browser.junit.jupiter.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.openqa.selenium.WebDriver;

class BrowserStoreTest {

	ExtensionContext context;
	Store store;

	@BeforeEach
	void setUp() {
		store = mock( Store.class );

		context = mock( ExtensionContext.class );
		when( context.getRequiredTestClass() ).thenCallRealMethod();
		when( context.getRequiredTestMethod() ).thenCallRealMethod();
		when( context.getStore( any() ) ).thenReturn( store );
	}

	@Test
	void put_should_query_correct_namespace( final TestInfo info ) {
		when( context.getTestClass() ).thenReturn( info.getTestClass() );
		when( context.getTestMethod() ).thenReturn( info.getTestMethod() );
		final Namespace namespace = expectedNamespace(); // For some reason context#getRequiredTestClass returns null if later

		final WebDriver driver = mock( WebDriver.class );

		BrowserStore.put( context, driver );

		verify( context ).getStore( namespace );
		verify( store ).put( any(), eq( driver ) );
	}

	@Test
	void get_should_query_correct_namespace( final TestInfo info ) {
		when( context.getTestClass() ).thenReturn( info.getTestClass() );
		when( context.getTestMethod() ).thenReturn( info.getTestMethod() );
		final Namespace namespace = expectedNamespace(); // For some reason context#getRequiredTestClass returns null if later

		final WebDriver driver = mock( WebDriver.class );
		when( store.get( any(), any() ) ).thenReturn( driver );

		final Optional<WebDriver> webDriver = BrowserStore.get( context );

		verify( context ).getStore( namespace );
		verify( store ).get( any(), eq( WebDriver.class ) );

		assertThat( webDriver ).hasValue( driver );
	}

	private Namespace expectedNamespace() {
		return Namespace.create( BrowserStore.class, context.getRequiredTestClass(), context.getRequiredTestMethod() );
	}
}
