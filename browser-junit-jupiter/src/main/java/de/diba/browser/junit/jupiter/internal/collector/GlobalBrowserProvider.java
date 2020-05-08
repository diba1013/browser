package de.diba.browser.junit.jupiter.internal.collector;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.ReflectionSupport;

import de.diba.browser.api.BrowserContext;
import de.diba.browser.api.BrowserConverter;
import de.diba.browser.api.Browsers;
import de.diba.browser.api.cookie.CookieContext;
import de.diba.browser.junit.jupiter.api.BrowserTest;
import de.diba.browser.junit.jupiter.api.provider.BrowserArgument;
import de.diba.browser.junit.jupiter.api.provider.BrowserConverterFactory;
import de.diba.browser.junit.jupiter.api.provider.BrowserProvider;
import de.diba.browser.junit.jupiter.api.provider.CookieProvider;
import de.diba.browser.junit.jupiter.api.provider.URLProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Builder
@RequiredArgsConstructor( access = AccessLevel.PACKAGE )
public class GlobalBrowserProvider implements BrowserProvider {

	@NonNull
	private final BrowserProvider local;

	@NonNull
	private final BrowserConverterFactory factory;
	@Singular( "cookie" )
	private final Set<CookieContext> cookies;
	@Singular( "url" )
	private final Set<String> urls;

	public static BrowserProvider of( final BrowserProvider local, final BrowserTest browser ) {
		return GlobalBrowserProvider.builder() //
				.local( local ) //
				.factory( createFactory( browser.factory() ) ) //
				.cookies( createCookies( browser.cookies() ) ) //
				.urls( createUrl( browser.url() ) ) //
				.build();
	}

	private static BrowserConverterFactory createFactory( final Class<? extends BrowserConverterFactory> factory ) {
		return ReflectionSupport.newInstance( factory );
	}

	private static Set<CookieContext> createCookies( final Class<? extends CookieProvider> cookies ) {
		final CookieProvider provider = ReflectionSupport.newInstance( cookies );
		return provider.get() //
				.collect( Collectors.toSet() );
	}

	private static Set<String> createUrl( final Class<? extends URLProvider> url ) {
		final URLProvider provider = ReflectionSupport.newInstance( url );
		return provider.get() //
				.collect( Collectors.toSet() );
	}

	@Override
	public Stream<BrowserArgument> provide( final ExtensionContext context ) {
		final BrowserConverter global = factory.create( Browsers.Services.Suppliers.none() );
		return local.provide( context ) //
				.flatMap( argument -> {
					final BrowserConverter local = argument.getConverter().orElse( global );
					return reconfigure( argument ) //
							.map( reconfigured -> BrowserArgument.of( reconfigured, local ) );
				} );
	}

	private Stream<BrowserContext> reconfigure( final BrowserArgument argument ) {
		final BrowserContext origin = argument.getContext();
		final List<CookieContext> cookies = joinCookies( origin.getCookies() );
		final String url = origin.getUrl();

		if ( urls.isEmpty() ) {
			return Stream.of( withUrl( origin, cookies, url ) );
		}

		if ( url == null || urls.contains( url ) ) {
			return urls.stream() //
					.map( urls -> withUrl( origin, cookies, urls ) );
		}

		return Stream.concat( Stream.of( url ), urls.stream() ) //
				.map( urls -> withUrl( origin, cookies, urls ) );
	}

	private BrowserContext withUrl( final BrowserContext origin, final List<CookieContext> cookies, final String url ) {
		return BrowserContext.builder() //
				.browser( origin.getBrowser() ) //
				.resolution( origin.getResolution() ) //
				.headless( origin.isHeadless() ) //
				.cookies( cookies ) //
				.url( url ) //
				.build();
	}

	private List<CookieContext> joinCookies( final List<CookieContext> cookies ) {
		return Stream.concat( this.cookies.stream(), cookies.stream() ) //
				.collect( Collectors
						.collectingAndThen( Collectors.groupingBy( CookieContext::getUrl, Collectors.toSet() ),
								this::merge ) );
	}

	private List<CookieContext> merge( final Map<String, Set<CookieContext>> map ) {
		return map.entrySet().stream() //
				.map( entry -> {
					final String url = entry.getKey();
					final Set<CookieContext> cookies = entry.getValue();
					return CookieContext.builder() //
							.url( url ) //
							.cookies( cookies.stream() //
									.map( CookieContext::getCookies ) //
									.flatMap( List::stream ) //
									.collect( Collectors.toList() ) ) //
							.build();
				} ) //
				.collect( Collectors.toList() );
	}
}
