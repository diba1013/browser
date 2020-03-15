package de.diba.browser.junit.jupiter.internal;

import java.util.List;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor( staticName = "of" )
public final class BrowserTestInvocationContext implements TestTemplateInvocationContext {

	private final BrowserTestNameFormatter formatter;
	private final BrowserArgument argument;

	@Override
	public String getDisplayName( final int invocationIndex ) {
		return formatter.format( argument.getContext() );
	}

	@Override
	public List<Extension> getAdditionalExtensions() {
		return List.of( //
				new CreateBrowser( argument ), //
				new BrowserTestParameterResolver(), //
				new InjectBrowserIntoFields(), //
				new InjectElementIntoFields() //
		);
	}

	@RequiredArgsConstructor
	private static class CreateBrowser implements BeforeEachCallback, AfterEachCallback {

		private final BrowserArgument argument;

		@Override
		public void beforeEach( final ExtensionContext context ) throws Exception {
			BrowserStore.put( context, argument.createDriver() );
		}

		@Override
		public void afterEach( final ExtensionContext context ) throws Exception {
			BrowserStore.quit( context );
		}
	}

	private static class BrowserTestParameterResolver extends TypeBasedParameterResolver<WebDriver> {

		@Override
		public WebDriver resolveParameter( final ParameterContext parameterContext,
				final ExtensionContext extensionContext ) throws ParameterResolutionException {
			return BrowserStore.get( extensionContext ).orElseThrow();
		}
	}

	private static class InjectBrowserIntoFields implements BeforeEachCallback, AfterEachCallback {

		@Override
		public void beforeEach( final ExtensionContext context ) {
			final WebDriver driver = BrowserStore.getRequired( context );
			FieldStore.injectDriver( context, driver );
		}

		@Override
		public void afterEach( final ExtensionContext context ) {
			FieldStore.injectDriver( context, null );
		}
	}

	private static class InjectElementIntoFields implements BeforeEachCallback {

		@Override
		public void beforeEach( final ExtensionContext context ) throws Exception {
			final WebDriver driver = BrowserStore.getRequired( context );
			PageFactory.initElements( driver, context.getRequiredTestInstance() );
		}
	}
}
