package com.sam.controller;

import com.sam.common_constant.CommonConstant;
import com.sam.dto.UserDto;
import com.sam.model.VerificationToken;
import com.sam.service.implementation.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sam.exception.UserException;
import com.sam.request.LoginRequest;
import com.sam.response.AuthResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserServiceImplementation userServiceImplementation;

    @Autowired
    public AuthController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request){
        try {
            // Call the createUserHandler method from the service layer
            ResponseEntity<AuthResponse> responseEntity = userServiceImplementation.createUserHandler(userDto,request);
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        } catch (UserException e) {
            // Handle exceptions and return an error response
            return new ResponseEntity<>(new AuthResponse(null, false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Call the signIn method from the service layer
            ResponseEntity<AuthResponse> responseEntity = userServiceImplementation.signIn(loginRequest);
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return new ResponseEntity<>(new AuthResponse(null, false), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = userServiceImplementation.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return CommonConstant.EXISTING_USER_VERIFICATION_MESSAGE;
        }
        String verificationResult = userServiceImplementation.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return CommonConstant.VERIFICATION_MESSAGE_C;
        }
        return CommonConstant.TOKEN_NOT_VALID;
    }


}
