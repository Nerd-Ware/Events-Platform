package com.example.eventplatform.repository;

import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.model.EventNeeds;
import org.springframework.data.repository.CrudRepository;

public interface EventNeedsRepository extends CrudRepository<EventNeeds,Integer> {
//    public EventNeeds findByEventNeedsId(int eventNeedsId);
}
