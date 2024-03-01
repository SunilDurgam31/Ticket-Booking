package com.example.demo.repos;

import com.example.demo.models.Category;
import com.example.demo.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
 
    Optional<Event> findById(Event eventId);

	List<Event> findByCategory(Category category);

	List<Event> findByCategoryNameAndLocation(String categoryName, String location);

	List<Event> findByLocation(String location);
}