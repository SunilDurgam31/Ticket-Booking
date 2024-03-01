package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.models.Event;
import com.example.demo.models.EventRequest;
import com.example.demo.models.Register;
import com.example.demo.models.Ticket;
import com.example.demo.models.TicketPurchaseRequest;
import com.example.demo.repos.CategoryRepository;
import com.example.demo.repos.EventRepository;
import com.example.demo.repos.EventsRepository;
import com.example.demo.repos.TicketInventoryRepository;
import com.example.demo.repos.TicketRepository;

import jakarta.transaction.Transactional;

import com.example.demo.models.TicketInventory;


import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

	 @Autowired
	    private EventRepository eventRepository;
	 @Autowired
	    private EventsRepository eventsRepository;
	 @Autowired
	    private CategoryRepository categoryRepository;
	 @Autowired
	    private TicketRepository ticketRepository;
	 @Autowired
	 private TicketInventoryRepository ticketInventoryRepository;
	

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Register registerEvent(Register register) {
        // Perform any additional business logic/validation if needed
        return eventRepository.save(register);
    }
    public Optional<Register> login(String emailOrMobileNumber, String password) {
        Optional<Register> register;

        
        if (emailOrMobileNumber.contains("@")) {
            
            register = eventRepository.findByEmailAndPassword(emailOrMobileNumber, password);
        } else {
            
            register = eventRepository.findByMobileNumberAndPassword(emailOrMobileNumber, password);
        }

        return register;
    }
    @Override
    public Optional<Register> getUserDetails(String emailOrMobileNumber) {
        if (emailOrMobileNumber.contains("@")) {
            return eventRepository.findByEmail(emailOrMobileNumber);
        } else {
            return eventRepository.findByMobileNumber(emailOrMobileNumber);
        }
    }


    @Override
    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }


    @Override
    public Event addEvent(EventRequest eventRequest) {
        String categoryName = eventRequest.getCategoryName();
        Category category = categoryRepository.findByNameIgnoreCase(categoryName);

        // If category does not exist, create it
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category = categoryRepository.save(category);
        }

        // Create the event and set its properties
        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setEventDate(eventRequest.getEventDate());
        event.setEventTime(eventRequest.getEventTime());
        event.setLocation(eventRequest.getLocation());
        event.setCategory(category);
        event.setDescription(eventRequest.getDescription());
        event.setTotalCapacity(eventRequest.getTotalCapacity());
        event.setAvailableTickets(eventRequest.getAvailableTickets());
        // Save the event
        return eventsRepository.save(event);
    }

    @Override
    public Event getEventById(Long eventId) {
        Optional<Event> optionalEvent = eventsRepository.findById(eventId);
        return optionalEvent.orElse(null);
    }
   
    @Override
    public Ticket purchaseTicket(Event event, Register register, BigDecimal price, int count,String ticketType) {
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setRegister(register);
        ticket.setPurchaseDateTime(LocalDateTime.now());
        ticket.setPrice(price);
        ticket.setCount(count);
        ticket.setTicketType(ticketType);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getTicketsByEvent(Event event) {
        return ticketRepository.findByEvent(event);
    }

    @Override
    public List<Ticket> getTicketsByRegister(Register register) {
        return ticketRepository.findByRegister(register);
    }

    @Override
    public void cancelTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }
    @Override
    public Ticket purchaseTicket(TicketPurchaseRequest purchaseRequest) {
        // 1. Create a new ticket record
        Ticket ticket = new Ticket();
        ticket.setEvent(purchaseRequest.getEvent());
        ticket.setRegister(purchaseRequest.getRegister());
        ticket.setPurchaseDateTime(LocalDateTime.now());
        ticket.setTicketType(purchaseRequest.getTicketType());// Set purchase date and time
        ticket.setPrice(purchaseRequest.getPrice());
        ticket.setCount(purchaseRequest.getCount());

        // Save the new ticket record
        ticket = ticketRepository.save(ticket);

        // 2. Update the ticket inventory
        updateTicketInventory(purchaseRequest.getEvent(), purchaseRequest.getCount());

        return ticket; // Return the purchased ticket
    }


    private void updateTicketInventory(Event event, int purchasedCount) {
        // Retrieve the current ticket inventory for the event
        TicketInventory ticketInventory = ticketInventoryRepository.findByEventId(event);

        if (ticketInventory != null) {
            // Update the quantity available in the ticket inventory
            int currentAvailableCount = ticketInventory.getQuantityAvailable();
            ticketInventory.setQuantityAvailable(currentAvailableCount - purchasedCount);

            // Update the total tickets sold
            int currentTotalTicketsSold = ticketInventory.getTotalTicketsSold();
            ticketInventory.setTotalTicketsSold(currentTotalTicketsSold + purchasedCount);

            // Update the total count
            int currentTotalCount = ticketInventory.getTotalCount();
            ticketInventory.setTotalCount(currentTotalCount + purchasedCount);

            // Save the updated ticket inventory
            ticketInventoryRepository.save(ticketInventory);
        }
    }



    @Override
    public TicketInventory getTicketInventoryForEvent(Event eventId) {
        // Fetch ticket inventory for the specified event from the database
        TicketInventory ticketInventory = ticketInventoryRepository.findByEventId(eventId);
        
        if (ticketInventory != null) {
            return ticketInventory;
        } else {
            // Calculate ticket inventory based on existing tickets for the event (if not stored in database)
            int totalTicketsSold = ticketRepository.countByEvent(eventId);
            int totalTicketsAvailable = getTotalTicketsAvailableForEvent(eventId);
            
            // Create a new TicketInventory object
            ticketInventory = new TicketInventory(eventId, totalTicketsAvailable, totalTicketsSold, totalTicketsAvailable);
            
            return ticketInventory;
        }
    }

    
    private int getTotalTicketsAvailableForEvent(Event eventId) {
        // Retrieve the event object using the event ID
        Event event = eventsRepository.findById(eventId).orElse(null);
        
        if (event != null) {
            // Get the total capacity of the event
            int availableTickets = event.getAvailableTickets();
            
            // Get the total number of tickets sold for the event
            int totalTicketsSold = ticketRepository.countByEvent(event);
            
            // Calculate the total tickets available by subtracting sold tickets from total capacity
            return availableTickets - totalTicketsSold;
        } else {
            // Handle case where event is not found
            return 0;
        }
    }

    
    @Override
    public List<TicketInventory> getTicketInventory() {
        List<TicketInventory> ticketInventoryList = new ArrayList<>();

        // Retrieve ticket inventory data from the database or calculate it based on existing tickets
        List<Event> events = eventsRepository.findAll();

        for (Event event : events) {
            int totalTicketsSold = ticketRepository.countByEvent(event);

            // Handle null totalCapacity using Optional
            int totalCapacity = Optional.ofNullable(event.getTotalCapacity()).orElse(0);

            // Calculate quantity available for the event
            int quantityAvailable = totalCapacity - totalTicketsSold;

            TicketInventory ticketInventory = new TicketInventory(event, quantityAvailable, totalTicketsSold,
                    totalTicketsSold + quantityAvailable);
            ticketInventoryList.add(ticketInventory);
        }

        return ticketInventoryList;
    }


    @Transactional
    public void saveTicketInventory(Event event, int quantityAvailable, int totalTicketsSold, int totalCount) {
        TicketInventory ticketInventory = new TicketInventory(event, quantityAvailable, totalTicketsSold, totalCount);
        ticketInventoryRepository.save(ticketInventory);
    }
    
    @Override
    public List<Event> getEventsByCategoryAndLocation(String categoryName, String location) {
        Category category = categoryRepository.findByNameIgnoreCase(categoryName);
        if (categoryName != null && location != null) {
            // Fetch events by category and location
            return eventsRepository.findByCategoryNameAndLocation(categoryName, location);
        } else if (categoryName != null) {
            // Fetch events by category
            return eventsRepository.findByCategory(category);
        } else if (location != null) {
            // Fetch events by location
            return eventsRepository.findByLocation(location);
        } else {
            // Fetch all events if no category or location specified
            return eventsRepository.findAll();
        }
    }

}