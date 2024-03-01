package com.example.demo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = " events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_time")
    private LocalTime eventTime;
    private String location;
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "name")
    private Category category;

    private String description;
    @Column(name = "total_capacity")
    private Integer totalCapacity; // New field
	@Column(name = "available_tickets")
	private int availableTickets;
	@Column(name = "ticket_price")
	private BigDecimal ticketPrice;
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public Long getId() {
		return id;
	}
	public Integer getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(Integer totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getAvailableTickets() {
		
		return availableTickets;
	}
	
	public void setAvailableTickets(int updatedAvailableTickets) {
	    this.availableTickets = updatedAvailableTickets;
	}
	

    // Constructors, getters, and setters
}