package com.example.orderpickup.repository;

import com.example.orderpickup.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, String> {

}
