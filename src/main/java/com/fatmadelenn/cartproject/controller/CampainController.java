package com.fatmadelenn.cartproject.controller;

import com.fatmadelenn.cartproject.model.Campaign;
import com.fatmadelenn.cartproject.model.dto.CampainDTO;
import com.fatmadelenn.cartproject.service.CampaignServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CampainController {

    @Autowired
    private CampaignServiceImpl campaignServiceImpl;

    @PostMapping(value = "/campain")
    public ResponseEntity<Object> createNewCampain(@RequestBody CampainDTO campainDTO) {
        return campaignServiceImpl.createNewCampain(campainDTO);
    }

    @GetMapping(value = "/campains")
    public List<Campaign> getAllCampain() {
        return campaignServiceImpl.getAllCampain();
    }
}
