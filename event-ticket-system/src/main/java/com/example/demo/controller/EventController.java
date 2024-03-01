package com.example.demo.controller;

import com.example.demo.Response.EventErrorResponse;

import com.example.demo.models.Event;
import com.example.demo.models.EventRequest;
import com.example.demo.models.LoginRequest;
import com.example.demo.models.PurchaseTicketRequest;
import com.example.demo.models.Register;
import com.example.demo.models.Ticket;
import com.example.demo.models.TicketInventory;

import com.example.demo.models.TicketPurchaseRequest;

import com.example.demo.services.EventService;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
    private EventService eventService;
	

   
    
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<Register> registerEvent(@RequestBody Register register) {
        Register registeredRegister = eventService.registerEvent(register);
        return new ResponseEntity<>(registeredRegister, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Register> registerOptional = eventService.login(loginRequest.getEmailOrMobileNumber(), loginRequest.getPassword());

        if (registerOptional.isPresent()) {
            Register register = registerOptional.get();
            // You can handle successful login for organization here
            return ResponseEntity.ok(register);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new EventErrorResponse("Invalid credentials."));
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/upcoming-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody EventRequest eventRequest) {
        Event event = eventService.addEvent(eventRequest);
        return ResponseEntity.ok(event);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/upcoming-events/{eventId}")
    public ResponseEntity<?> getEventDetails(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody PurchaseTicketRequest purchaseTicketRequest) {
        Ticket ticket = eventService.purchaseTicket(purchaseTicketRequest.getEvent(),
                                                      purchaseTicketRequest.getRegister(),
                                                      purchaseTicketRequest.getPrice(),
                                                      
                                                      purchaseTicketRequest.getCount(),
                                                      purchaseTicketRequest.getTicketType());
        
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = eventService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable Long eventId) {
        Event event = new Event();
        event.setId(eventId);
        List<Ticket> tickets = eventService.getTicketsByEvent(event);
        return ResponseEntity.ok(tickets);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/register/{registerId}")
    public ResponseEntity<List<Ticket>> getTicketsByRegister(@PathVariable Long registerId) {
        Register register = new Register();
        register.setId(registerId);
        List<Ticket> tickets = eventService.getTicketsByRegister(register);
        return ResponseEntity.ok(tickets);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long ticketId) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        eventService.cancelTicket(ticket);
        return ResponseEntity.noContent().build();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/purchases")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody TicketPurchaseRequest purchaseRequest) {
        Ticket ticket = eventService.purchaseTicket(purchaseRequest);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/inventory")
    public ResponseEntity<List<TicketInventory>> getTicketInventory() {
        List<TicketInventory> inventory = eventService.getTicketInventory();
        return ResponseEntity.ok(inventory);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/inventory/{eventId}")
    public ResponseEntity<TicketInventory> getTicketInventoryForEvent(@PathVariable Event eventId) {
        TicketInventory inventory = eventService.getTicketInventoryForEvent(eventId);
        return ResponseEntity.ok(inventory);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/filter")
    public List<Event> getEventsByCategoryAndLocation(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String location
    ) {
        return eventService.getEventsByCategoryAndLocation(categoryName, location);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user-details")
    public ResponseEntity<?> getUserDetails(@RequestParam String emailOrMobileNumber) {
        Optional<Register> registerOptional = eventService.getUserDetails(emailOrMobileNumber);

        if (registerOptional.isPresent()) {
            Register register = registerOptional.get();
            return ResponseEntity.ok(register);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



   
    
}