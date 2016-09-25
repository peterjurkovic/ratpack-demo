package com.peterjurkovic.upstream.handler;

import static com.peterjurkovic.upstream.handler.BaseResponse.error;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static ratpack.jackson.Jackson.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.registry.Registry;

public class UserAgentVersioningHandler implements Handler{
	
	private final static Logger LOG = LoggerFactory.getLogger(UserAgentVersioningHandler.class);
	
	private final static String ERROR_MSG = "Unsupported User-Agent";
	
	public enum ClientVersion{
		V1("Microservice v1.0"),
		V2("Microservice v2.0"),
		V3("Microservice v3.0"),;
		
		private final String version;
		
		private ClientVersion(String version){
			this.version = version;
		}
		
		static ClientVersion ofUserAgent(String strVersion){
			for(ClientVersion ver : values()){
				if(ver.version.equals(strVersion)){
					return ver;
				}
			}
			return null;
		}
		
	}
	
	@Override
	public void handle(Context ctx) throws Exception {
		String userAgent = ctx.getRequest().getHeaders().get("User-Agent");
		ClientVersion clientVersion = ClientVersion.ofUserAgent(userAgent);
		
		if(clientVersion == null){
			LOG.warn("No ClientVersion specified [userAgent={}]", userAgent);
			renderError(ctx);
		}else{
			ctx.next(Registry.single(clientVersion));
		}
	}

	private void renderError(Context ctx) throws Exception {
		ctx.getResponse().status( BAD_REQUEST.code() );
		ctx.byContent( spec -> spec
				.json(() -> {
					ctx.render( json( error( ERROR_MSG ) ));
				})
				.html(() -> {
					ctx.render( "<h1>400 Bad Request</h1><div>"+ ERROR_MSG + "</div>" );
				})
				.noMatch(() -> {
					ctx.render( ERROR_MSG );
				})
	   );
		
	}

}
