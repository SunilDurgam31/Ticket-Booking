package com.example.demo.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.*;
@Entity
@Table(name = "ticketinventory")
@Getter
@Setter
@NoArgsConstructor
public class TicketInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event eventId;

    @Column(name = "quantity_available")
    private int quantityAvailable;

    @Column(name = "total_tickets_sold")
    private int totalTicketsSold;

    @Column(name = "total_count")
    private int totalCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEventId() {
		return eventId;
	}

	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public int getTotalTicketsSold() {
		return totalTicketsSold;
	}

	public void setTotalTicketsSold(int totalTicketsSold) {
		this.totalTicketsSold = totalTicketsSold;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public TicketInventory(Event eventId, int quantityAvailable, int totalTicketsSold, int totalCount) {
        this.eventId = eventId;
        this.quantityAvailable = quantityAvailable;
        this.totalTicketsSold = totalTicketsSold;
        this.totalCount = totalCount;
    }

	
}
