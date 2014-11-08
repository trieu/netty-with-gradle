package com.mc2ads.server;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;

public class SimpleHttpServer {

	protected String host;
	protected int port;

	final protected boolean isAddressAlreadyInUse(String host, int port) {
		int timeout = 500;
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), timeout);
			socket.close();
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	private HttpServer checkAndCreateHttpServer(String host, int port) {
		if (isAddressAlreadyInUse(host, port)) {
			System.err.println(host + ":" + port + " isAddressAlreadyInUse!");
			System.exit(1);
			return null;
		}
		try {
			this.host = host;
			this.port = port;
			Vertx vertx = VertxFactory.newVertx();
			return vertx.createHttpServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	final public void registerHttpHandler(String host, int port,
			Handler<HttpServerRequest> handler) {
		HttpServer server = checkAndCreateHttpServer(host, port);
		if (server == null) {
			return;
		}
		server.requestHandler(handler).listen(port, host);
		System.out.println("Started server Ok at "+host+":"+port);
		foreverLoop(1000);
	}

	public static void shutdown() {
		System.out.println("Bye, now exiting ");
		sleep(1000);
		System.exit(1);
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void foreverLoop(long sleepMillis) {
		while (true) {
			sleep(sleepMillis);
		}
	}

}
