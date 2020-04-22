package com.example.demo.customer.service;

import com.example.demo.customer.dao.CustomerDao;
import com.example.demo.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer findCustomer (String account) {
        Customer customer = new Customer();
        if (account!=null) {
            customer = customerDao.findOneByAccount(account);
        }
        return customer;
    }
}
