package com.londonpeople.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.londonpeople.model.People;
import com.londonpeople.service.PeopleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author boney
 *
 */
@RestController
@RequestMapping("/london")
@Api(value = "CountryResource")
public class PeopleController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PeopleController.class);
	
	@Autowired
	private PeopleService peopleServc;
	
	/**
	 * Method to return residents of London and resident within 50 miles of London
	 * @return List
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@ApiOperation(httpMethod = "GET", value = "Get all people living in London and within 50 miles of London")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "People not found."),
            @ApiResponse(code = 500, message = "The people cannot be fetched.")
    })
	@GetMapping("/residents-neighbours")
	public ResponseEntity<List<People>> getLondonersAndNeighbours() throws InterruptedException, ExecutionException {
		LOG.info("Inside method to fetch people of london & nearby...");
		return new ResponseEntity<>(peopleServc.getLondonersAndNeighbours(), HttpStatus.OK);
	}

}
