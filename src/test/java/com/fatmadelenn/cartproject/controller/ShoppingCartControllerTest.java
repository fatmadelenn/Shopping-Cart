package com.fatmadelenn.cartproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatmadelenn.cartproject.model.*;
import com.fatmadelenn.cartproject.model.dto.ShoppingCartDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void createNewShoppingCart() throws Exception {
        int numberOfProduct = 5;
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setNumberOfProduct(numberOfProduct);
        shoppingCartDTO.setProductId(1);

        MvcResult mvcResult = mockMvc.perform(
                post("/api/shopping-cart/add")
                        .content(objectMapper.writeValueAsString(shoppingCartDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(equalTo(200)));
    }

    @Test
    public void createNewShoppingCartWithId() throws Exception {
        int shoppingCartId = 3;
        int numberOfProduct = 5;
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setNumberOfProduct(numberOfProduct);
        shoppingCartDTO.setProductId(1);

        MvcResult mvcResult = mockMvc.perform(
                post("/api/shopping-cart/add/" + shoppingCartId)
                        .content(objectMapper.writeValueAsString(shoppingCartDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(equalTo(200)));
    }


    @Test
    public void getTotalPrice() throws Exception {
        int shoppingCartId = 3;
        double total = totalPrice(shoppingCartId);

        MvcResult mvcResultTotal = mockMvc.perform(
                get("/api/shopping-cart/total-price/" + shoppingCartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResultTotal.getResponse().getContentAsString(), is(equalTo(Double.toString(total))));
    }

    @Test
    public void getTotalCampain() throws Exception {
        int shoppingCartId = 3;
        double discount = getCampainDisconunt(shoppingCartId);
        MvcResult mvcResultTotal = mockMvc.perform(
                get("/api/shopping-cart/total-campain/" + shoppingCartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResultTotal.getResponse().getContentAsString(), is(equalTo(Double.toString(discount))));
    }


    @Test
    public void getCouponDiscount() throws Exception {
        int shoppingCartId = 3;
        double couponDiscount = 0;

        MvcResult mvcResultTotal = mockMvc.perform(
                get("/api/shopping-cart/total-coupon/" + shoppingCartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        couponDiscount = couponDiscount(shoppingCartId);
        assertThat(mvcResultTotal.getResponse().getContentAsString(), is(equalTo(Double.toString(couponDiscount))));
    }

    @Test
    public void getPrintShoppingCartTest() throws Exception {
        int shoppingCartId = 3;
        MvcResult mvcResultCartPrint = mockMvc.perform(
                get("/api/shopping-cart/print/" + shoppingCartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Map<String, Object> reason = new HashMap<>();
        List<Double> prices = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        List<Integer> numberOfProduct = new ArrayList<>();
        List<String> categoryProduct = new ArrayList<>();
        Map<String, Double> discount = new HashMap<>();
        List<CartInfo> cartInfoList = new ArrayList<>();

        cartInfoGet(shoppingCartId, prices, productId, numberOfProduct, categoryProduct);

        getCartInfoList(cartInfoList, productId, prices, numberOfProduct, categoryProduct);
        Map<Category, List<CartInfo>> categoryListMap = getCategoryAndCartInfoMap(cartInfoList);

        double afterAmountDiscount = totalPrice(shoppingCartId) - (getCampainDisconunt(shoppingCartId) + couponDiscount(shoppingCartId));

        reason.put("productInCart", categoryListMap);
        reason.put("Total Price", totalPrice(shoppingCartId));
        discount.put("Campain Discount", getCampainDisconunt(shoppingCartId));
        discount.put("Coupon Discount", couponDiscount(shoppingCartId));
        reason.put("Total Discount", discount);
        reason.put("Delivery Cost", getDeliveryCost(cartInfoList));
        reason.put("Total Amount After Discounts", afterAmountDiscount);
        reason.put("Total", afterAmountDiscount + getDeliveryCost(cartInfoList));

        ObjectMapper mapper = new ObjectMapper();

        assertThat(mapper.writeValueAsString(reason), is(equalTo(mvcResultCartPrint.getResponse().getContentAsString())));
    }

    public double totalPrice(int shoppingCartId) throws Exception {
        double total = 0;
        List<Double> prices = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        List<Integer> numberOfProduct = new ArrayList<>();
        List<String> categoryProduct = new ArrayList<>();

        cartInfoGet(shoppingCartId, prices, productId, numberOfProduct, categoryProduct);

        Map<Double, Integer> totalMap = new HashMap<>();
        for (int i = 0; i < prices.size(); i++) {
            if (!totalMap.containsKey(prices.get(i))) {
                totalMap.put(prices.get(i), numberOfProduct.get(i));
            } else {
                totalMap.put(prices.get(i), totalMap.get(prices.get(i)) + numberOfProduct.get(i));
            }
        }

        for (Map.Entry<Double, Integer> entryCartInfo : totalMap.entrySet()) {
            double numberOfProductMap = entryCartInfo.getKey();
            int priceMap = entryCartInfo.getValue();
            total = total + priceMap * numberOfProductMap;
        }
        return total;
    }


    public void cartInfoGet(int shoppingCartId, List<Double> prices, List<Long> productId, List<Integer> numberOfProduct, List<String> categoryProduct) throws Exception {
        MvcResult mvcResultCart = mockMvc.perform(
                get("/api/shopping-cart/" + shoppingCartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        int cartBegin = mvcResultCart.getResponse().getContentAsString().indexOf("cartInfos");
        String[] splitCartInfo = mvcResultCart.getResponse().getContentAsString().substring(cartBegin).split("\\[")[1].replaceAll("[\\[\\](){}]", "").split(",");
        for (int i = 0; i < splitCartInfo.length; i++) {
            if (splitCartInfo[i].contains("productPrice")) {
                prices.add(Double.parseDouble(splitCartInfo[i].split(":")[1]));
            }
            if (splitCartInfo[i].contains("numberOfProduct")) {
                numberOfProduct.add(Integer.parseInt(splitCartInfo[i].split(":")[1]));
            }
            if (splitCartInfo[i].contains("categoryTitle")) {
                categoryProduct.add(splitCartInfo[i].replaceAll("[{\"\"}]", "").split(":")[2].replaceAll("\"", ""));
            }
            if (splitCartInfo[i].contains("productId")) {
                productId.add(Long.parseLong(splitCartInfo[i].split(":")[2]));
            }
        }
    }

    public double getCampainDisconunt(int shoppingCartId) throws Exception {
        List<Double> prices = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        List<Integer> numberOfProduct = new ArrayList<>();
        List<String> categoryProduct = new ArrayList<>();
        List<Double> discountCampainList = new ArrayList<>();
        List<Campaign> campaignList = new ArrayList<>();

        getAllCampain(campaignList);

        cartInfoGet(shoppingCartId, prices, productId, numberOfProduct, categoryProduct);
        List<CartInfo> cartInfoList = new ArrayList<>();

        getCartInfoList(cartInfoList, productId, prices, numberOfProduct, categoryProduct);
        Map<String, Map<Double, Integer>> mapForCartInfo = getMapForCartInfo(cartInfoList);

        for (Map.Entry<String, Map<Double, Integer>> entryCartInfo : mapForCartInfo.entrySet()) {
            String productCategory = entryCartInfo.getKey();
            Map<Double, Integer> productInfo = entryCartInfo.getValue();
            for (Campaign campaign : campaignList) {
                if (campaign.getCategoryForCampain().getCategoryTitle().equals(productCategory)) {
                    for (Map.Entry<Double, Integer> entryProductInfo : productInfo.entrySet()) {
                        double campainDiscount = 0;
                        double price = entryProductInfo.getKey();
                        int number = entryProductInfo.getValue();
                        if (number > campaign.getNumberOfProducts()) {
                            if (campaign.getDiscountType().equals(DiscountType.RATE)) {
                                campainDiscount = price * (int) campaign.getDiscount() / 100;
                            }
                            if (campaign.getDiscountType().equals(DiscountType.AMOUNT)) {
                                campainDiscount = campaign.getDiscount();
                            }
                        }
                        discountCampainList.add(campainDiscount);
                    }
                }
            }
        }
        return applyDiscounts(discountCampainList);
    }


    public void getAllCampain(List<Campaign> campaignList) throws Exception {
        MvcResult mvcResultCampains = mockMvc.perform(
                get("/api/campains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        String[] campains = mvcResultCampains.getResponse().getContentAsString().replaceAll("[\\[\\]]", "").split("\\},\\{");
        for (int i = 0; i < campains.length; i++) {
            String[] parseCampain = campains[i].split(",");
            double discount = 0;
            double numberOfProducts = 0;
            DiscountType discountType = DiscountType.RATE;
            Category category = new Category();
            for (int j = 0; j < parseCampain.length; j++) {
                if (parseCampain[j].contains("\"discount\"")) {
                    discount = Double.parseDouble(parseCampain[j].split(":")[1]);
                } else if (parseCampain[j].contains("numberOfProducts")) {
                    numberOfProducts = Double.parseDouble(parseCampain[j].split(":")[1]);
                } else if (parseCampain[j].contains("discountType")) {
                    if (parseCampain[j].split(":")[1].contains("RATE")) {
                        discountType = DiscountType.RATE;
                    } else {
                        discountType = DiscountType.AMOUNT;
                    }
                } else if (parseCampain[j].contains("categoryTitle")) {
                    category.setCategoryTitle(parseCampain[j].replaceAll("[{\"\"}]", "").split(":")[2]);
                }
            }
            Campaign campaign = new Campaign(category, discount, numberOfProducts, discountType);
            campaignList.add(campaign);
        }
    }

    public double applyDiscounts(List<Double> discountCampainList) {
        if (!discountCampainList.isEmpty()) {
            Collections.sort(discountCampainList);
            return discountCampainList.get(discountCampainList.size() - 1);
        }
        return 0;
    }

    public void getCartInfoList(List<CartInfo> cartInfoList, List<Long> productId, List<Double> prices, List<Integer> numberOfProduct, List<String> categoryProduct) {
        for (int i = 0; i < prices.size(); i++) {
            Product product = new Product(productId.get(i), prices.get(i), new Category(categoryProduct.get(i)));
            cartInfoList.add(new CartInfo(product, numberOfProduct.get(i)));
        }
    }

    public double couponDiscount(int shoppingCartId) throws Exception {
        Coupon coupon = new Coupon(100, 10, DiscountType.RATE);
        double afterCampainPrice = totalPrice(shoppingCartId) - getCampainDisconunt(shoppingCartId);
        if (afterCampainPrice >= coupon.getPrice()) {
            return afterCampainPrice * (int) coupon.getDiscount() / 100;
        }
        return 0;
    }

    public Map<String, Map<Double, Integer>> getMapForCartInfo(List<CartInfo> cartInfoList) {
        Map<String, Map<Double, Integer>> mapForCartInfo = new HashMap<>();
        for (CartInfo cartInfo : cartInfoList) {
            Map<Double, Integer> productInfoMap = new HashMap<>();
            Category productCategory = cartInfo.getProduct().getCategory();
            if (!mapForCartInfo.containsKey(productCategory.getCategoryTitle())) {
                productInfoMap.put(cartInfo.getProduct().getProductPrice() * cartInfo.getNumberOfProduct(), cartInfo.getNumberOfProduct());
                mapForCartInfo.put(productCategory.getCategoryTitle(), productInfoMap);
            } else {
                Map<Double, Integer> mapPriceAndNumber = mapForCartInfo.get(productCategory.getCategoryTitle());
                for (Map.Entry<Double, Integer> entryPriceAndNumber : mapPriceAndNumber.entrySet()) {
                    double price = entryPriceAndNumber.getKey();
                    int number = entryPriceAndNumber.getValue();
                    price = price + cartInfo.getProduct().getProductPrice() * cartInfo.getNumberOfProduct();
                    number = number + cartInfo.getNumberOfProduct();
                    productInfoMap.put(price, number);
                    mapForCartInfo.put(productCategory.getCategoryTitle(), productInfoMap);
                }
            }
        }
        return mapForCartInfo;
    }

    public Map<Category, List<CartInfo>> getCategoryAndCartInfoMap(List<CartInfo> cartInfoList) {
        Map<Category, List<CartInfo>> categoryListMap = new HashMap<>();
        for (CartInfo cartInfo : cartInfoList) {
            Category productCategory = cartInfo.getProduct().getCategory();
            if (!categoryListMap.containsKey(productCategory)) {
                categoryListMap.put(productCategory, new ArrayList<CartInfo>(Arrays.asList(cartInfo)));
            } else {
                List<CartInfo> infoList = categoryListMap.getOrDefault(productCategory, new ArrayList<>());
                infoList.add(cartInfo);
                categoryListMap.put(productCategory, infoList);
            }
        }
        return categoryListMap;
    }

    public double getDeliveryCost(List<CartInfo> cartInfoList) {
        ShoppingCart shoppingCart = new ShoppingCart(6, cartInfoList);
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2.9, 2.9);
        return deliveryCostCalculator.calculateFor(shoppingCart);
    }
}
