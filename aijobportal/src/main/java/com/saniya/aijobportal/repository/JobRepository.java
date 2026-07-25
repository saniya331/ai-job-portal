package com.saniya.aijobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saniya.aijobportal.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
