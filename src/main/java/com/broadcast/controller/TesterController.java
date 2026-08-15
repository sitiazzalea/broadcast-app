package com.broadcast.controller;

import com.broadcast.model.AgeCategory;
import com.broadcast.model.Customer;
import com.broadcast.service.BroadcastService;
import com.broadcast.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tester")
@Tag(name = "Tester", description = "API Collections for Testing")
public class TesterController {
    private final CustomerService customerService;
    private final BroadcastService broadcastService;

    public TesterController(CustomerService customerService, BroadcastService broadcastService) {
        this.customerService = customerService;
        this.broadcastService = broadcastService;
    }

    @GetMapping(path = "/get-customers-by-age")
    public ResponseEntity<List<Customer>> getCustomersByAge(@RequestParam AgeCategory ageCategory) {
        return ResponseEntity.ok().body(customerService.findCustomers(ageCategory));
    }

//    @GetMapping(path = "/get-broadcast-session")
//    public ResponseEntity<String> getBroadcastSession(@RequestParam AgeCategory ageCategory,
//                                                      @RequestParam String message){
//        return ResponseEntity.ok().body(broadcastService.createBroadcastSession(ageCategory, message));
//    }
}
