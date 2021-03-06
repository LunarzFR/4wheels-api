package com.wheels.wheelsapi;

import com.wheels.wheelsapi.controller.UserController;
import com.wheels.wheelsapi.entity.User;
import com.wheels.wheelsapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService service;

	private MockMvc mvc;

	@BeforeEach
	void setUp(){
		mvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	@WithUserDetails("admin")
	void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
		Mockito.when( this.service.getAllUsers() ).thenReturn( Collections.singletonList(
				new User(1, "test", "test", "test", true, "test" )));

		MvcResult result = mvc.perform( MockMvcRequestBuilders.get( "/user" ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.[0].lastName" ).value( "test" ) )
				.andReturn();

		System.out.println( result.getResponse().getContentAsString() );
	}

}
