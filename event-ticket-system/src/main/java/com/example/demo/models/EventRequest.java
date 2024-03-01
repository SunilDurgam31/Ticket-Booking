package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


import lombok.*;

//EventRequest.java
@Getter
@Setter
public class EventRequest {
 private String name;
 private LocalDate eventDate;
 private LocalTime eventTime;
 
private String location;
 private String categoryName;
 private String description;
 private BigDecimal ticketPrice;
	
	public BigDecimal getTicketPrice() {
	return ticketPrice;
}
public void setTicketPrice(BigDecimal ticketPrice) {
	this.ticketPrice = ticketPrice;
}
	private Integer totalCapacity;
  
	public Integer getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(Integer totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public int getAvailableTickets() {
		return availableTickets;
	}
	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}
	private int availableTickets;
 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public LocalTime getEventTime() {
		return eventTime;
	}
	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}