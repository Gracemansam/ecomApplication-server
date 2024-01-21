package com.sam.service;

import java.util.List;

import com.sam.dto.UserDto;
import com.sam.exception.UserException;
import com.sam.model.User;
import com.sam.model.VerificationToken;
import com.sam.request.LoginRequest;
import com.sam.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {



    ResponseEntity<AuthResponse> createUserHandler(UserDto userDto, HttpServletRequest request) throws UserException;

    ResponseEntity<AuthResponse> signIn(LoginRequest loginRequest);

    Authentication authenticate(String username, String password);

    public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();

    void saveUserVerificationToken(User theUser, String token);

    String validateToken(String theToken);

    VerificationToken findByToken(String token);
}
