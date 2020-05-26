package com.fatmadelenn.cartproject.repository;

import com.fatmadelenn.cartproject.model.CartInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartInfoRepository extends JpaRepository<CartInfo, Long> {
}
