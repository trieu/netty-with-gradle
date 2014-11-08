package com.mc2ads.server;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;

public class MainApp {

	public static void main(String[] args) {
		Handler<HttpServerRequest> handler = new Handler<HttpServerRequest>() {
			public void handle(HttpServerRequest request) {
				String path = request.absoluteURI().getPath();
				HttpServerResponse response = request.response();
				if (path.equals("/cmd/kill")) {
					response.end("Exiting...");
					SimpleHttpServer.shutdown();
					return;
				} else if (path.equals("/cmd/ping")) {
					response.end("PONG");
					return;
				}
				Person person = new Person("Netty Http Server");
				response.end("Hello, welcome you to "+person);
			}
		};
		String host = "localhost";
		int port = 9999;
		SimpleHttpServer httpServer = new SimpleHttpServer();
		httpServer.registerHttpHandler(host, port, handler);
	}
}
