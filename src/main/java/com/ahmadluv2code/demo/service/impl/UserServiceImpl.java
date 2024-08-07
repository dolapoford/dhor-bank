package com.ahmadluv2code.demo.service.impl;

import com.ahmadluv2code.demo.dto.AccountInfo;
import com.ahmadluv2code.demo.dto.BankResponse;
import com.ahmadluv2code.demo.dto.EmailDetails;
import com.ahmadluv2code.demo.dto.UserRequest;
import com.ahmadluv2code.demo.entity.User;
import com.ahmadluv2code.demo.repository.UserRepository;
import com.ahmadluv2code.demo.uilts.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository ;

    @Autowired
    EmailService emailService ;
    @Override
    public BankResponse createAccount(UserRequest userRequest) {


        /**
         *  Creating an account - saving a new user into the database
         *  check if user already have an account
         * */

        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

            User savedUser = userRepository.save(newUser);
            //Send email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulation! Your Account Has Been  Successfully Created\n Your Account Details: \n" +
                        "Account name: " +savedUser.getFirstName()+ " "  + savedUser.getOtherName() + "\nAccount Number: " + savedUser.getAccountNumber())
                .build();
            emailService.sendEmailAlert(emailDetails);
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(savedUser.getAccountBalance())
                            .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() )
                            .accountNumber(savedUser.getAccountNumber())
                            .build())
                    .build();
    }
}
