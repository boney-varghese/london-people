package com.londonpeople.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.londonpeople.model.People;
import com.londonpeople.service.PeopleService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PeopleController.class)
public class PeopleControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PeopleService peopleServc;
	
	List<People> mockPeople = Arrays.asList(new People(135, "Mechelle", "Boam", 
			"mboam3q@thetimes.co.uk", "113.71.242.187", -6.5115909, 105.652983));
	
	@Test
	public void getLondonersAndNeighbours() throws Exception {

		Mockito.when(peopleServc.getLondonersAndNeighbours()).thenReturn(mockPeople);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/london/residents-neighbours").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{\"id\":135,\"email\":\"mboam3q@thetimes.co.uk\","
				+ "\"latitude\":-6.5115909,\"longitude\":105.652983,\"first_name\":"
				+ "\"Mechelle\",\"last_name\":\"Boam\",\"ip_address\":\"113.71.242.187\"}]";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}


}
