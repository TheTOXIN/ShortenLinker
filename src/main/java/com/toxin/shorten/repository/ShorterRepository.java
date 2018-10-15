package com.toxin.shorten.repository;

import com.toxin.shorten.entity.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShorterRepository extends JpaRepository<Shorter, UUID> {

}
