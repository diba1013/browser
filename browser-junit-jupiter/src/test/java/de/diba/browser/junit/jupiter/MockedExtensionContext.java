package de.diba.browser.junit.jupiter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.ReflectionSupport;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MockedExtensionContext {

	public static ExtensionContext of( final TestInfo info ) {
		final ExtensionContext context = mock( ExtensionContext.class );
		when( context.getTestClass() ).thenReturn( info.getTestClass() );
		when( context.getRequiredTestClass() ).thenCallRealMethod();
		when( context.getTestMethod() ).thenReturn( info.getTestMethod() );
		when( context.getRequiredTestMethod() ).thenCallRealMethod();
		when( context.getStore( any() ) ).thenReturn( mock( ExtensionContext.Store.class ) );
		return context;
	}

	public static ExtensionContext of( final Class<? extends ExtensionContextProvider> provider ) {
		final ExtensionContext context = mock( ExtensionContext.class );
		when( context.getTestClass() ).thenReturn( Optional.of( provider ) );
		when( context.getRequiredTestClass() ).thenCallRealMethod();
		when( context.getTestMethod() ).thenReturn( ReflectionSupport.findMethod( provider, "method" ) );
		when( context.getRequiredTestMethod() ).thenCallRealMethod();
		when( context.getStore( any() ) ).thenReturn( mock( ExtensionContext.Store.class ) );
		return context;
	}

	public static ExtensionContext of( final ExtensionContextProvider provider ) {
		final ExtensionContext context = of( provider.getClass() );
		when( context.getTestInstance() ).thenReturn( Optional.of( provider ) );
		when( context.getRequiredTestInstance() ).thenCallRealMethod();
		when( context.getStore( any() ) ).thenReturn( mock( ExtensionContext.Store.class ) );
		return context;
	}

	public interface ExtensionContextProvider {

		void method();
	}
}
