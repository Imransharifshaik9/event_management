package com.fsd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
