package com.example.eventplatform.repository;

import com.example.eventplatform.model.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback,Integer> {
}
