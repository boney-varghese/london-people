package com.londonpeople.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.londonpeople.model.People;

public interface PeopleService {

	List<People> getLondonersAndNeighbours() throws InterruptedException, ExecutionException;

}
