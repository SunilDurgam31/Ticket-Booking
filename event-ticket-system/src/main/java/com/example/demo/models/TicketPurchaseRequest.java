package com.example.demo.models;

import java.math.BigDecimal;


import lombok.*;

@Getter
@Setter

public class TicketPurchaseRequest {
    private Register register;
    private Event event;
    private String ticketType;
    
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	
	  private BigDecimal price;
	    
	    private int count;
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
	
    
}