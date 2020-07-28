package com.payment.razorpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RazorpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	private static Class<RazorpayApplication> applicationClass = RazorpayApplication.class;
}
