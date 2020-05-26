package com.fatmadelenn.cartproject.repository;

import com.fatmadelenn.cartproject.model.Campaign;
import com.fatmadelenn.cartproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampainRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findByCategoryForCampain(Category category);
}
