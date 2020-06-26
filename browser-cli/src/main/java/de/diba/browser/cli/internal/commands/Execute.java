package de.diba.browser.cli.internal.commands;


import java.util.concurrent.Callable;

import de.diba.browser.cli.api.BrowserScript;
import de.diba.browser.cli.internal.BrowserExecutor;
import de.diba.browser.cli.internal.mixin.BrowserContextMixin;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@RequiredArgsConstructor
@Command( name = "execute", mixinStandardHelpOptions = true )
public class Execute implements Callable<Integer> {

	private final BrowserExecutor executor;

	@Spec
	private CommandSpec spec;

	@Mixin
	private BrowserContextMixin context;

	@Parameters
	private BrowserScript script;

	@Override
	public Integer call() throws Exception {
		executor.execute( context.convert(), script );
		return spec.exitCodeOnSuccess();
	}
}
