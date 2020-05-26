package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Campaign;
import com.fatmadelenn.cartproject.model.Category;
import com.fatmadelenn.cartproject.model.dto.CampainDTO;
import com.fatmadelenn.cartproject.repository.CampainRepository;
import com.fatmadelenn.cartproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements CampainService {

    @Autowired
    private CampainRepository campainRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Object> createNewCampain(CampainDTO campainDTO) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryTitle(campainDTO.getCategory().getCategoryTitle());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Optional<Campaign> campaignOptional = campainRepository.findByCategoryForCampain(category);
            if (campaignOptional.isPresent()) {
                Campaign campaign = campaignOptional.get();
                campainRepository.delete(campaign);
            }
            Campaign newCampaign = new Campaign(categoryOptional.get(), campainDTO.getDiscount(), campainDTO.getNumberOfProducts(), campainDTO.getDiscountType());
            return new ResponseEntity<>(campainRepository.save(newCampaign), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
        }
    }

    public List<Campaign> getAllCampain() {
        return campainRepository.findAll();
    }
}
