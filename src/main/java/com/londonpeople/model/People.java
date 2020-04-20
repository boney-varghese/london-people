package com.londonpeople.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class People {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("ip_address")
	private String ipAddress;
	
	@JsonProperty("latitude")
	private double latitude;
	
	@JsonProperty("longitude")
	private double longitude;
	
	public People(int id, String firstName, String lastName, String email, String ipAddress, double latitude,
			double longitude) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.ipAddress = ipAddress;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}
