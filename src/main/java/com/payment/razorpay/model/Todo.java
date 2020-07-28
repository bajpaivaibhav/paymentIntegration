package com.payment.razorpay.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todos")
@Getter
@Setter
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(min = 1, max = 35, message = "Name must be greater than 1 and less than 35 chars!!")
	@Pattern(regexp = "^[A-Za-z0-9!@#$%&()\\-,.'\\/+\\s]*$", message = "Valid chars for username are ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !@#$%&()-,.'/")
	private String userName;

	@Size(min = 10, message = "Enter at least 10 Characters...")
	private String description;

	@Size(min = 1, max = 35, message = "Amount must be greater than 1 and less than 35 chars!!")
	@Pattern(regexp = "^[0-9]*$", message = "Valid input for amount are 0123456789")
	private long amount;

	@Size(min = 1, max = 35, message = "Email must be greater than 1 and less than 35 chars!!")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Valid email looks like abc@abc.com")
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date targetDate;
	
	private String orderid;

	public Todo() {
		super();
	}

	public Todo(String userName, String description,
			long amount, String email) {
		super();
		this.userName = userName;
		this.description = description;
		this.amount = amount;
		this.email = email;
	}

}