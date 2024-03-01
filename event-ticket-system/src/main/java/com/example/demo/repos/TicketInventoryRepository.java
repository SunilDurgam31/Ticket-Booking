package com.example.demo.repos;
import com.example.demo.models.Event;

import com.example.demo.models.TicketInventory;


	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	@Repository
	public interface TicketInventoryRepository extends JpaRepository<TicketInventory, Long> {
		 TicketInventory findByEventId(Event event);

		

}
