package com.saniya.aijobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saniya.aijobportal.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompanyContainingIgnoreCase(String company);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByRequiredSkillsContaining(String skill);

    List<Job> findByPostedBy(String postedBy);

    long countByPostedBy(String postedBy);
}