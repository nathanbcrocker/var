package com.digiup.algo.var;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.digiup.algo.var.actors.StockLoadingManager;
import com.digiup.algo.var.actors.StockManager;
import com.digiup.algo.var.domain.Stock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class VaRApplication {

	public static void main(String[] args) {
		//SpringApplication.run(VaRApplication.class, args);
		ActorSystem system = ActorSystem.create("var-system");
		try {
			ActorRef msft = system.actorOf(StockManager.props("MSFT"), "msft-supervisor");

			final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
			final Future<Object> future = Patterns.ask(msft, new StockManager.StockQuery("MSFT"), timeout);

			try {
				Stock stock = (Stock) Await.result(future, timeout.duration());
				System.out.println(stock.getTicker());

			} catch (Exception e) { e.printStackTrace(); }
		} finally {
			system.terminate();
		}
	}
}
