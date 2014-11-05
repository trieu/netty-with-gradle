package com.mc2ads.server;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public class MainApp {

	public static void main(String[] args) {
		Handler<HttpServerRequest> handler = new Handler<HttpServerRequest>() {

			public void handle(HttpServerRequest request) {
				if (request.absoluteURI().getPath().equals("/cmd/kill")) {
					request.response().end("Exiting...");
					SimpleHttpServer.shutdown();
					return;
				} else if (request.absoluteURI().getPath().equals("/cmd/ping")) {
					request.response().end("PONG");
					return;
				}
				Person person = new Person("Netty Http Server");
				request.response().end("Hello, welcome you to "+person);
			}
		};
		String host = "localhost";
		int port = 9999;
		SimpleHttpServer httpServer = new SimpleHttpServer();
		httpServer.registerWorkerHttpHandler(host, port, handler);

	}
}
