package com.ahmadluv2code.demo.service.impl;

import com.ahmadluv2code.demo.dto.BankResponse;
import com.ahmadluv2code.demo.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);

}
