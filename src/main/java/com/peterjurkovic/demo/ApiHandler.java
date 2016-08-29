package com.peterjurkovic.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peterjurkovic.demo.componet.UserAgentHandler;
import com.peterjurkovic.demo.componet.UserAgentHandler.Browser;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class ApiHandler implements Handler{
	
	private final static Logger logger = LoggerFactory.getLogger(ApiHandler.class);
	
	@Override
	public void handle(Context ctx) throws Exception {
		Browser browser = ctx.get(UserAgentHandler.Browser.class);
		logger.info(browser.toString());
		switch (browser) {
		case FIREFOX:
				ctx.render("Firefox");
			break;
		case CHROME:
			ctx.render("Chrome");
		break;
		default:
			ctx.render("Unknow");
			break;
		}
	}
}
