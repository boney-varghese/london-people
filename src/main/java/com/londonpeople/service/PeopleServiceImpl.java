package com.londonpeople.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.londonpeople.model.People;

@Service
public class PeopleServiceImpl implements PeopleService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PeopleServiceImpl.class);

	@Value("${base.url}") 
	private String baseUrl;

	public static final double RADIUS = 6372.8; // In kilometers

	public static final double LONDON_LATITUDE = 51.5074;

	public static final double LONDON_LONGITUDE = 0.1278;

	private static final double KILOMETER_TO_MILE = 0.621371;

	RestTemplate restTemplate = new RestTemplate();

	/**
	 * Method to return residents of London and resident within 50 miles of London
	 * @return List
	 */
	@Override
	public List<People> getLondonersAndNeighbours() throws InterruptedException, ExecutionException {
		LOG.info("Inside method to fetch people of london & nearby...");
		CompletableFuture<List<People>> londoners = getLondonResidnets();
		CompletableFuture<List<People>> neighbours = getLondonNeighbours();
		List<People> finalList = new ArrayList<>();
		Stream.of(londoners, neighbours)
		.map(CompletableFuture::join)
		.map(finalList::addAll).count();
		return finalList;
	}

	/**
	 * Method which invokes API to return people living in London
	 * @return List
	 */
	@Async
	private CompletableFuture<List<People>> getLondonResidnets() {
		LOG.info("Inside method to fetch people of london...");
		String url = baseUrl + "/city/London/users";
		People[] response  = restTemplate.getForObject(url, People[].class);
		return CompletableFuture.completedFuture(Arrays.asList(response));
	}

	/**
	 * Method which invokes API to fetch all list of users and returns people living within
	 * 50 miles of London
	 * @return List
	 */
	@Async
	private CompletableFuture<List<People>> getLondonNeighbours() {
		LOG.info("Inside method to fetch people nearby...");
		String url = baseUrl + "/users";
		People[] response  = restTemplate.getForObject(url, People[].class);
		List<People> neighbours = Arrays.asList(response).stream()
				.parallel()
				.filter(x -> haversine(LONDON_LATITUDE, LONDON_LONGITUDE, x.getLatitude(), x.getLongitude()) <= 50)
				.sorted(Comparator.comparingLong(People::getId))
				.collect(Collectors.toList());
		return CompletableFuture.completedFuture(neighbours);
	}

	/**
	 * Method which uses Haversine formula to calculate distance between two locations 
	 * @param sourceLat
	 * @param sourceLon
	 * @param destinationLat
	 * @param destinationLon
	 * @return double
	 */
	public double haversine(final double sourceLat, final double sourceLon,
			final double destinationLat, final double destinationLon) {
		double differenceLat = Math.toRadians(destinationLat - sourceLat);
		double differenceLng = Math.toRadians(destinationLon - sourceLon);
		double sourceLatInRadians = Math.toRadians(sourceLat);
		double destinationLatInRadians = Math.toRadians(destinationLat);

		double a = Math.pow(Math.sin(differenceLat / 2),2) + Math.pow(Math.sin(differenceLng / 2),2) * Math.cos(sourceLatInRadians) * Math.cos(destinationLatInRadians);
		double c = 2 * Math.asin(Math.sqrt(a));
		return RADIUS * c * KILOMETER_TO_MILE;
	}
}
