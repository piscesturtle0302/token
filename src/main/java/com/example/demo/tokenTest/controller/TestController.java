package com.example.demo.tokenTest.controller;

import com.example.demo.common.enums.WebErrCode;
import com.example.demo.common.util.ResultHelper;
import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.tokenTest.form.TokenForm;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Test")
public class TestController {
    @Autowired
    CustomerService customerService = new CustomerService();
    
    @RequestMapping(value = "/hello", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> hello(HttpServletResponse response) {
        
        Customer customer = customerService.findCustomer(131244L);
        return ResultHelper.returnResult(WebErrCode.err200, customer);

    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> user(HttpServletRequest request) {

        Customer customer = customerService.findCustomer("A128976080");

        return ResultHelper.returnResult(WebErrCode.err200, customer);

    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> admin(HttpServletRequest request) {

        return ResultHelper.returnResult(WebErrCode.err200, "admin");

    }

}
