package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.*;
import com.fatmadelenn.cartproject.model.dto.ShoppingCartDTO;
import com.fatmadelenn.cartproject.repository.CampainRepository;
import com.fatmadelenn.cartproject.repository.ProductRepository;
import com.fatmadelenn.cartproject.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    CampainRepository campainRepository;

    public ResponseEntity<Object> addItem(ShoppingCartDTO shoppingCartDTO) {
        List<CartInfo> cartInfos = new ArrayList<>();
        Optional<Product> productOptional = productRepository.findById(shoppingCartDTO.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartInfo cartInfo = new CartInfo(product, shoppingCartDTO.getNumberOfProduct());
            cartInfos.add(cartInfo);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setCartInfos(cartInfos);
            return new ResponseEntity<>(shoppingCartRepository.save(shoppingCart), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateCart(long shoppingCartId, ShoppingCartDTO shoppingCartDTO) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            List<CartInfo> cartInfos = shoppingCartOptional.get().getCartInfos();
            Optional<Product> productOptional = productRepository.findById(shoppingCartDTO.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                CartInfo cartInfo = new CartInfo(product, shoppingCartDTO.getNumberOfProduct());
                cartInfos.add(cartInfo);
                ShoppingCart shoppingCart = shoppingCartOptional.get();
                shoppingCart.setCartInfos(cartInfos);
                return new ResponseEntity<>(shoppingCartRepository.save(shoppingCart), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Cart not found!", HttpStatus.NOT_FOUND);
        }
    }

    public double getTotalPrice(long shoppinggCartId) {
        double totalPrice = 0;
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppinggCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            for (CartInfo cartInfo : shoppingCart.getCartInfos()) {
                totalPrice = totalPrice + cartInfo.getNumberOfProduct() * cartInfo.getProduct().getProductPrice();
            }
        }
        return totalPrice;
    }

    public Map<Category, Map<Double, Integer>> mapForCartInfo(long shoppinggCartId) {
        Map<Category, Map<Double, Integer>> mapForCartInfo = new HashMap<>();
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppinggCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            for (CartInfo cartInfo : shoppingCart.getCartInfos()) {
                Map<Double, Integer> productInfoMap = new HashMap<>();
                Category productCategory = cartInfo.getProduct().getCategory();

                if (!mapForCartInfo.containsKey(productCategory)) {
                    productInfoMap.put(cartInfo.getProduct().getProductPrice()*cartInfo.getNumberOfProduct(), cartInfo.getNumberOfProduct());
                    mapForCartInfo.put(productCategory, productInfoMap);
                } else {
                    Map<Double, Integer> mapPriceAndNumber = mapForCartInfo.get(productCategory);
                    for (Map.Entry<Double, Integer> entryPriceAndNumber : mapPriceAndNumber.entrySet()) {
                        double price = entryPriceAndNumber.getKey();
                        int number = entryPriceAndNumber.getValue();
                        price = price + cartInfo.getProduct().getProductPrice()*cartInfo.getNumberOfProduct();
                        number = number + cartInfo.getNumberOfProduct();
                        productInfoMap.put(price, number);
                        mapForCartInfo.put(productCategory, productInfoMap);
                    }
                }
            }
        }
        return mapForCartInfo;
    }

    public double getTotalAmountAfterDiscounts(long shoppingCartId) {
        double total = getTotalPrice(shoppingCartId);
        double campainDiscount = getCampaignDiscount(shoppingCartId);
        double couponDiscount = getCouponDiscount(shoppingCartId);
        total = total - (campainDiscount + couponDiscount);
        return total;
    }

    public double getCouponDiscount(long shoppingCardId) {
        double totalCampainDiscount = getTotalPrice(shoppingCardId) - getCampaignDiscount(shoppingCardId);
        Coupon coupon = new Coupon(100, 10, DiscountType.RATE);
        return applyCoupon(totalCampainDiscount, coupon);
    }

    public double getCampaignDiscount(long shoppingCardId) {
        List<Campaign> campaigns = campainRepository.findAll();
        List<Double> campainDiscountList = new ArrayList<>();
        for (Map.Entry<Category, Map<Double, Integer>> entryCartInfo : mapForCartInfo(shoppingCardId).entrySet()) {
            Category category = entryCartInfo.getKey();
            Map<Double, Integer> productInfoMap = entryCartInfo.getValue();
            forCampainCategory(campainDiscountList, campaigns, category, productInfoMap);
        }
        return applyDiscounts(campainDiscountList);
    }

    public void forCampainCategory(List<Double> campainDiscountList, List<Campaign> campaigns, Category category, Map<Double, Integer> productInfoMap) {
        for (Campaign campaign : campaigns) {
            if (campaign.getCategoryForCampain().getCategoryTitle().equals(category.getCategoryTitle())) {
                makeDiscount(campainDiscountList, productInfoMap, campaign);
            }
        }
    }

    public void makeDiscount(List<Double> campainDiscountList, Map<Double, Integer> productInfoMap, Campaign campaign) {
        for (Map.Entry<Double, Integer> entryProductInfo : productInfoMap.entrySet()) {
            double campainDiscount = 0;
            double price = entryProductInfo.getKey();
            int number = entryProductInfo.getValue();
            if (number > campaign.getNumberOfProducts()) {
                if (campaign.getDiscountType().equals(DiscountType.RATE)) {
                    campainDiscount = price * (int) campaign.getDiscount() / 100;
                }
                else if (campaign.getDiscountType().equals(DiscountType.AMOUNT)) {
                    campainDiscount = campaign.getDiscount();
                }
            }
            campainDiscountList.add(campainDiscount);
        }
    }

    public double getDeliveryCost(long shoppinggCartId) {
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2.9, 2.9);
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppinggCartId);
        if (shoppingCartOptional.isPresent()) {
            return deliveryCostCalculator.calculateFor(shoppingCartOptional.get());
        }
        return 0;
    }

    public Map<String, Object> print(long shoppingCartId) { //yazdır
        Map<String, Object> reason = new HashMap<>();
        Map<String, Double> discount = new HashMap<>();
        double total = getTotalPrice(shoppingCartId);
        Map<Category, List<CartInfo>> categoryListMap = getCategoryAndCartInfoMap(shoppingCartId);
        reason.put("productInCart", categoryListMap);
        reason.put("Total Price", total);
        discount.put("Campain Discount", getCampaignDiscount(shoppingCartId));
        discount.put("Coupon Discount", getCouponDiscount(shoppingCartId));
        reason.put("Total Discount", discount);
        reason.put("Delivery Cost", getDeliveryCost(shoppingCartId));
        reason.put("Total Amount After Discounts", getTotalAmountAfterDiscounts(shoppingCartId));
        reason.put("Total", getTotalAmountAfterDiscounts(shoppingCartId) + getDeliveryCost(shoppingCartId));
        return reason;
    }

    public Map<Category, List<CartInfo>> getCategoryAndCartInfoMap(long shoppingCartId) {
        Map<Category, List<CartInfo>> mapForCartInfo = new HashMap<>();
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            for (CartInfo cartInfo : shoppingCart.getCartInfos()) {
                Category productCategory = cartInfo.getProduct().getCategory();
                if (!mapForCartInfo.containsKey(productCategory)) {
                    mapForCartInfo.put(productCategory, new ArrayList<>(Arrays.asList(cartInfo)));
                } else {
                    List<CartInfo> cartInfoList = mapForCartInfo.getOrDefault(productCategory, new ArrayList<>());
                    cartInfoList.add(cartInfo);
                    mapForCartInfo.put(productCategory, cartInfoList);
                }
            }
        }
        return mapForCartInfo;
    }

    public double applyDiscounts(List<Double> campainDiscountList) {
        if (campainDiscountList.isEmpty()) {
            return 0;
        }
        Collections.sort(campainDiscountList);
        return campainDiscountList.get(campainDiscountList.size() - 1);
    }

    public double applyCoupon(double total, Coupon coupon) {
        if (total >= coupon.getPrice()) {
            return total * (int) coupon.getDiscount() / 100;
        }
        return 0;
    }

    public ShoppingCart getShoppingCart(long shoppingCartId) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCart.isPresent()) {
            return shoppingCart.get();
        }
        return null;
    }
}
