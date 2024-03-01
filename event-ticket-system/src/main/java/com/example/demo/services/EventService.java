package com.example.demo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.Event;
import com.example.demo.models.EventRequest;
import com.example.demo.models.Register;
import com.example.demo.models.Ticket;
import com.example.demo.models.TicketInventory;
import com.example.demo.models.TicketPurchaseRequest;

public interface EventService {
    Register registerEvent(Register register);
    Optional<Register> login(String emailOrMobileNumber, String password);
    Optional<Register> getUserDetails(String emailOrMobileNumber);

    Event addEvent(EventRequest eventRequest);

    Event getEventById(Long eventId);
	List<Event> getAllEvents();
	
    List<Ticket> getAllTickets();
    List<Ticket> getTicketsByEvent(Event event);
    List<Ticket> getTicketsByRegister(Register register);
    void cancelTicket(Ticket ticket);
    
    Ticket purchaseTicket(TicketPurchaseRequest purchaseRequest);
    List<TicketInventory> getTicketInventory();
    TicketInventory getTicketInventoryForEvent(Event eventId);
   
	Ticket purchaseTicket(Event event, Register register, BigDecimal price, int count, String ticketType);
	
	List<Event> getEventsByCategoryAndLocation(String categoryName, String location);
	

   
}