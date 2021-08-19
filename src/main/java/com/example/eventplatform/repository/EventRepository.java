package com.example.eventplatform.repository;

import com.example.eventplatform.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,Integer> {
}
