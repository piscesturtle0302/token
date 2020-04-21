package com.example.demo.customer.dao;

import com.example.demo.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDao extends JpaRepository<Customer,Long> {

    @Query(value = " select * from Customer where ACCOUNT = ?1 ", nativeQuery = true)
    public Customer findOneByAccount(String account);

}
