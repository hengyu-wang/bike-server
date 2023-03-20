package com.example.demo.repository;

import com.example.demo.entity.subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface subscriberRepository extends JpaRepository<subscriber,String> {
}
