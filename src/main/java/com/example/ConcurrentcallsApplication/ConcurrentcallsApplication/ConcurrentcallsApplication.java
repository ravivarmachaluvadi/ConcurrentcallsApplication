package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class ConcurrentcallsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrentcallsApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Executor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(250);
		executor.setThreadNamePrefix("my-thread");
		executor.setMaxPoolSize(250);
		executor.setQueueCapacity(10000);
		executor.initialize();
		return executor;
	}

}
