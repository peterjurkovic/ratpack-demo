package com.peterjurkovic.demo.componet;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.registry.Registry;

public class UserAgentHandler implements Handler {

	public enum Browser {
		FIREFOX("firefox"), CHROME("chrome"), UNKNOWN("");

		private final String matcher;

		private Browser(String matcher) {
			this.matcher = matcher;
		}

		public String getMatcher() {
			return matcher;
		}

		public static Browser of(String userAgent) {
			if (userAgent != null) {
				for (Browser browser : values()) {
					if (browser.getMatcher().contains(userAgent)) {
						return browser;
					}
				}
			}
			return UNKNOWN;
		}
	}

	@Override
	public void handle(Context ctx) throws Exception {
		String userAgent = ctx.getRequest().getHeaders().get("User-agent");
		Browser browser = Browser.of(userAgent);
		ctx.next(Registry.single(browser));
	}

}
