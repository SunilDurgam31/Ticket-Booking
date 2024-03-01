package com.example.demo.repos;

import com.example.demo.models.Event;
import com.example.demo.models.Register;
import com.example.demo.models.Ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	 List<Ticket> findByEvent(Event event);

	List<Ticket> findByRegister(Register register);

	int countByEvent(Event event);

}
