package com.mc2ads.server.test;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.mc2ads.server.Person;
import com.mc2ads.server.util.HttpClientUtil;

public class PerformanceTestHttpServer {
	static AtomicInteger validCount = new AtomicInteger(0);
	static AtomicInteger invalidCount = new AtomicInteger(0);
	static Stopwatch stopwatch = Stopwatch.createUnstarted();

	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	@Test	
	@PerfTest(invocations = 30000, threads = 300)
	@Required(max = 10000, average = 600)
	public void testPing() throws Exception {		
		String url = "http://localhost:9999/cmd/ping";
		String rs = HttpClientUtil.executeGet(url);
		
		if (rs.length()>3) {
			int c = validCount.incrementAndGet();
			System.out.println(c+" \n");			
		} else {
			System.out.println(rs);
			invalidCount.incrementAndGet();
			throw new IllegalArgumentException("Bad response!");
		}		
	}
	
	@Before
	public void beginTest(){
		stopwatch.start();
		System.out.println("-------------------------------------");
		System.out.println("valid " + validCount.get());
		System.out.println("invalid " + invalidCount.get());
		System.out.println("-------------------------------------");
	}
	
	@After
	public void finishTest(){
		System.out.println("-------------------------------------");
		System.out.println("valid " + validCount.get());
		System.out.println("invalid " + invalidCount.get());
		System.out.println("-------------------------------------");
		stopwatch.stop();
		System.out.println("finished in milliseconds: "+stopwatch.elapsed(TimeUnit.MILLISECONDS));
	}
	
}
