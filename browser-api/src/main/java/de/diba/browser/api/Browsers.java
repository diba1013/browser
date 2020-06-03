package de.diba.browser.api;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.safari.SafariDriverService;

import de.diba.browser.internal.converter.AdvancedBrowserConverter;
import de.diba.browser.internal.service.ChromeBrowserServiceConverter;
import de.diba.browser.internal.service.EdgeBrowserServiceConverter;
import de.diba.browser.internal.service.GeckoBrowserServiceConverter;
import de.diba.browser.internal.service.InternetExplorerBrowserServiceConverter;
import de.diba.browser.internal.service.OperaBrowserServiceConverter;
import de.diba.browser.internal.service.SafariBrowserServiceConverter;
import de.diba.browser.internal.service.supplier.AllBrowserServiceSupplier;
import de.diba.browser.internal.service.supplier.EitherBrowserServiceSupplier;
import de.diba.browser.internal.service.supplier.NoBrowserServiceSupplier;
import de.diba.browser.internal.service.supplier.OnlyBrowserServiceSupplier;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@UtilityClass
@Accessors( fluent = true )
public class Browsers {

	public static WebDriver create( final BrowserContext context ) throws Exception {
		return create( context, Converters.create() );
	}

	public static WebDriver create( final BrowserContext context, final BrowserConverter converter ) throws Exception {
		return converter.convert( context );
	}

	@UtilityClass
	@Accessors( fluent = true )
	public static class Converters {

		@Getter( lazy = true )
		private static final BrowserConverter create = create( Services.Suppliers.none() );

		public static BrowserConverter create( final BrowserServiceSupplier service ) {
			return new AdvancedBrowserConverter( service );
		}
	}

	@UtilityClass
	@Accessors( fluent = true )
	public static class Services {

		@Getter( lazy = true )
		private final BrowserServiceConverter<ChromeDriverService> chrome = new ChromeBrowserServiceConverter();

		@Getter( lazy = true )
		private final BrowserServiceConverter<EdgeDriverService> edge = new EdgeBrowserServiceConverter();

		@Getter( lazy = true )
		private final BrowserServiceConverter<GeckoDriverService> firefox = new GeckoBrowserServiceConverter();

		@Getter( lazy = true )
		private final BrowserServiceConverter<InternetExplorerDriverService> internetExplorer = new InternetExplorerBrowserServiceConverter();

		@Getter( lazy = true )
		private final BrowserServiceConverter<OperaDriverService> opera = new OperaBrowserServiceConverter();

		@Getter( lazy = true )
		private final BrowserServiceConverter<SafariDriverService> safari = new SafariBrowserServiceConverter();

		@UtilityClass
		@Accessors( fluent = true )
		public static class Suppliers {

			@Getter( lazy = true )
			private final BrowserServiceSupplier none = new NoBrowserServiceSupplier();

			@Getter( lazy = true )
			private final BrowserServiceSupplier all = new AllBrowserServiceSupplier();

			public static BrowserServiceSupplier only( final Set<BrowserType> browsers,
					final BrowserServiceSupplier supplier ) {
				return browsers.isEmpty() ? none() : new OnlyBrowserServiceSupplier( browsers, supplier );
			}

			public static BrowserServiceSupplier either( final List<BrowserServiceSupplier> suppliers ) {
				return suppliers.isEmpty() ? none() : new EitherBrowserServiceSupplier( suppliers );
			}
		}
	}
}
