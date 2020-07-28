package com.payment.razorpay.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RazorpayOrderOutput {
	@JsonProperty("razorpay_payment_id")
	public String razorpayPaymentId;
	@JsonProperty("razorpay_order_id")
	public String razorpayOrderId;
	@JsonProperty("razorpay_signature")
	public String razorpaySignature;
}
