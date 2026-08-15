package com.broadcast.service;

import com.broadcast.mapper.CustomerMapper;
import com.broadcast.model.AgeCategory;
import com.broadcast.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerMapper customerMapper;
    private final AgeCategoryResolver ageCategoryResolver;

    public List<Customer> findCustomers(AgeCategory category) {

        AgeCategoryResolver.DateRange range = ageCategoryResolver.resolve(category);

        return customerMapper.findByDateOfBirthRange(
                range.getStartDate(),
                range.getEndDate()
        );
    }
}
