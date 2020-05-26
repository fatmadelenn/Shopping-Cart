package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Campaign;
import com.fatmadelenn.cartproject.model.dto.CampainDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CampainService {

    ResponseEntity<Object> createNewCampain(CampainDTO campainDTO);

    List<Campaign> getAllCampain();

}
