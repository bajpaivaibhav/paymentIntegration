package com.payment.razorpay.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment.razorpay.model.RazorpayOrderOutput;
import com.payment.razorpay.model.Todo;
import com.payment.razorpay.service.TodoService;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j(topic = "RegLogging")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@Value("${razorpay.key}")
	private String razorpayKey;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		try {
			String name = getLoggedInUserName(model);
			model.put("todos", todoService.getTodosByUser(name));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo());
		return "todo";
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam long id) {
		todoService.deleteTodo(id);
		// service.deleteTodo(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam long id, ModelMap model) {
		Todo todo = todoService.getTodoById(id).get();
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}

		todo.setUserName(getLoggedInUserName(model));
		todoService.updateTodo(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model,@Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}
		todo.setUserName(getLoggedInUserName(model));
		todoService.saveTodo(todo);
		return "redirect:/list-todos";

	}

	@RequestMapping(value = "/charge", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public String charge(RazorpayOrderOutput razorpayOrderOutput) {
		log.info("response received");
		
		String paymentId = razorpayOrderOutput.getRazorpayPaymentId();
		String razorpaySignature = razorpayOrderOutput.getRazorpaySignature();
		String orderId = razorpayOrderOutput.getRazorpayOrderId();

		JSONObject options = new JSONObject();

		if (StringUtils.isNotBlank(paymentId) && StringUtils.isNotBlank(razorpaySignature)
				&& StringUtils.isNotBlank(orderId)) {
			try {
				options.put("razorpay_payment_id", paymentId);
				options.put("razorpay_order_id", orderId);
				options.put("razorpay_signature", razorpaySignature);
				boolean isEqual = Utils.verifyPaymentSignature(options, this.razorpayKey);

				if (isEqual) {
					return "redirect:/index";
				}
			} catch (RazorpayException e) {
				log.error("Exception caused because of " + e.getMessage());
				return "redirect:/failure";
				// return Response.status(Status.BAD_REQUEST).build();
			}
		}
		return "redirect:/failure";
	}
}
