package de.diba.browser.cli.internal.mixin;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserType;
import de.diba.browser.api.resolution.Resolution;
import lombok.Data;
import picocli.CommandLine.Option;

@Data
public class BrowserContextMixin {

	@Option( names = { "--browser", "-b" }, defaultValue = "chrome" )
	private BrowserType browser;

	@Option( names = { "--resolution", "-r" }, defaultValue = "1280x800" )
	private Resolution resolution;

	@Option( names = { "--headless", "-H" }, negatable = true, defaultValue = "false" )
	private boolean headless;

	@Option( names = { "--url", "-u" } )
	private String url;

	public BrowserContext convert() {
		return BrowserContext.builder() //
				.browser( browser ) //
				.resolution( resolution ) //
				.headless( headless ) //
				.url( url ) //
				.build();
	}
}
