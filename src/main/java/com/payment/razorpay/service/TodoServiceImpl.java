package com.payment.razorpay.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.payment.razorpay.model.Todo;
import com.payment.razorpay.repository.TodoRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "RegLogging")
public class TodoServiceImpl implements TodoService {

	@Value("${razorpay.id}")
	private String razorpayId;

	@Value("${razorpay.key}")
	private String razorpayKey;

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public List<Todo> getTodosByUser(String user) {
		return todoRepository.findByUserName(user);
	}

	@Override
	public Optional<Todo> getTodoById(long id) {
		return todoRepository.findById(id);
	}

	@Override
	public void updateTodo(Todo todo) {
		todoRepository.save(todo);
	}

	@Override
	public void addTodo(String name, String desc, Date targetDate, boolean isDone) {

		todoRepository.save(new Todo());

	}

	@Override
	public void deleteTodo(long id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			todoRepository.delete(todo.get());
		}
	}

	@Override
	public void saveTodo(Todo todo) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBasicAuth(razorpayId, razorpayKey);

		try {
			JSONObject options = new JSONObject();
			options.put("amount", todo.getAmount()); // Note: The amount should be in paise.
			options.put("currency", "INR");
			options.put("receipt", "txn_123456");
			options.put("payment_capture", todo.getId());

			RazorpayClient razorpayClient = new RazorpayClient(this.razorpayId, this.razorpayKey);

			Order order = razorpayClient.Orders.create(options);
			todo.setOrderid(order.get("id"));
			log.info("order id : " + order.get("id"));
			todoRepository.save(todo);

		} catch (RuntimeException | RazorpayException e) {
			log.error("some problem occured while sending razorpay request", e);
		}

	}
}