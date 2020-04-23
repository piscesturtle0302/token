package com.example.demo.tokenTest.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenForm {
    String account;
    String name;
    List<String> authority;
}
