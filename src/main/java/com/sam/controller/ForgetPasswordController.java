package com.sam.controller;

import com.sam.service.implementation.*;
import com.sam.utils.ApplicationUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/forgetPassword")
@RequiredArgsConstructor

public class ForgetPasswordController {
    private  final UserServiceImplementation userServiceImplementation;

    private final ForgetPasswordService forgetPasswordService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        try {
            forgetPasswordService.initiateForgetPassword(email);
            return "OTP sent successfully";
        } catch (Exception e) {
            return "Error sending OTP: " + e.getMessage();
        }
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String enteredOtp) {
        try {
            if (forgetPasswordService.verifyOtp(email, enteredOtp)) {
                forgetPasswordService.passwordResetLink(email, enteredOtp);
                return "OTP verified successfully, and password reset link sent to email";
            } else {
                return "Invalid OTP";
            }
        } catch (Exception e) {
            return "Error verifying OTP: " + e.getMessage();
        }
    }

    @PostMapping("/reset-password/{email}/{otp}")
    public String resetPassword(@PathVariable String email, @PathVariable String otp, @RequestParam String newPassword) {
        try {
            forgetPasswordService.resetPassword(email, otp,newPassword);
            return "Password reset successfully";
        } catch (Exception e) {
            return "Error resetting password: " + e.getMessage();
        }
    }


}
