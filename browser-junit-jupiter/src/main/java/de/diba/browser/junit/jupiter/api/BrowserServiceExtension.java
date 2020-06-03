package de.diba.browser.junit.jupiter.api;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.diba.browser.junit.jupiter.internal.service.BrowserServiceSupplierStore;

public class BrowserServiceExtension implements BeforeAllCallback, AfterAllCallback {

	@Override
	public void beforeAll( final ExtensionContext context ) throws Exception {
		BrowserServiceSupplierStore.store( context );
	}

	@Override
	public void afterAll( final ExtensionContext context ) throws Exception {
		BrowserServiceSupplierStore.quit( context );
	}
}
