package com.payment.razorpay.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.payment.razorpay.model.Todo;
import com.payment.razorpay.service.TodoService;

@TestInstance(Lifecycle.PER_CLASS)
class TodoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TodoService todoService;

	@InjectMocks
	private TodoController todoController;

	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
	}

	@Test
	void todoTest() {
		Todo todo = new Todo();
		Model model;
		BindingResult bindingResult;

		doThrow(IllegalArgumentException.class).when(todoService).saveTodo(todo);
		//MvcResult result = mockMvc.perform(post("/add-todo").flashAttr	("todo", todo);
		//assertEquals(200, result.getResponse().getStatus());
		verify(todoService, times(1)).saveTodo(Mockito.any());
		verifyNoMoreInteractions(todoService);

	}

}
