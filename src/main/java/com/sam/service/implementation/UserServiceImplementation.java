package com.sam.service.implementation;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.sam.common_constant.CommonConstant;
import com.sam.dto.UserDto;
import com.sam.converter.UserConverter;

import com.sam.exception.BusinessException;
import com.sam.exception.ErrorModel;
import com.sam.model.VerificationToken;
import com.sam.repository.VerificationTokenRepository;
import com.sam.request.LoginRequest;
import com.sam.response.AuthResponse;
import com.sam.service.CartService;
import com.sam.service.UserService;
import com.sam.utils.ApplicationUrl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.sam.config.JwtTokenProvider;
import com.sam.model.User;
import com.sam.repository.UserRepository;

@Service
@Lazy
public class UserServiceImplementation implements UserService {
	
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	private final PasswordEncoder passwordEncoder;

	private final CustomUserDetails customUserDetails;
	private CartService cartService;
	private final UserConverter userConverter;
	private final VerificationTokenRepository tokenRepository;
	private final ApplicationEventPublisher publisher;
	private final ApplicationUrl applicationUrl;


	public UserServiceImplementation(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, CustomUserDetails customUserDetails, UserConverter userConverter, VerificationTokenRepository tokenRepository, ApplicationEventPublisher publisher, ApplicationUrl applicationUrl) {
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
		this.customUserDetails = customUserDetails;
		this.userConverter = userConverter;
		this.tokenRepository = tokenRepository;
		this.publisher = publisher;
		this.applicationUrl = applicationUrl;
	}
	@Autowired
	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}


	@Override
	public ResponseEntity<AuthResponse> createUserHandler(UserDto userDto, HttpServletRequest request){

		Optional<User> isEmailExist = userRepository.findByEmail(userDto.getEmail());

		// Check if user with the given email already exists
		if (isEmailExist.isPresent()) {
			ErrorModel errorModel = ErrorModel.builder()
					.code(CommonConstant.USER_ALREADY_EXIST_CODE)
					.message(CommonConstant.USER_ALREADY_EXIST)
					.timestamp(LocalDateTime.now())
							.build();
			throw new BusinessException(errorModel);


		}

		// Create new user
		User newUser = userConverter.convertDTOtoEntity(userDto);
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		newUser.setRole("CUSTOMER");


		User savedUser= userRepository.save(newUser);

		cartService.createCart(savedUser);
//	try {
//
//		publisher.publishEvent(new RegistrationCompleteEvent(newUser, applicationUrl.applicationUrl(request)));
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw new UserException(CommonConstant.USER_REGISTRATION_FAILED);
//	}

	Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		AuthResponse authResponse= new AuthResponse(token,true);
//		UserDto savedUserResponse = userConverter.convertEntityToDTO(savedUser);

		return new ResponseEntity<>(authResponse,HttpStatus.CREATED);

	}

@Override
	public ResponseEntity<AuthResponse> signIn(LoginRequest loginRequest) {
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		System.out.println(username +" ----- "+password);

		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);


		String token = jwtTokenProvider.generateToken(authentication);
		AuthResponse authResponse= new AuthResponse();

		authResponse.setStatus(true);
		authResponse.setJwt(token);

		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}
@Override
public Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetails.loadUserByUsername(username);

		System.out.println("sign in userDetails - "+userDetails);

		if (userDetails == null) {
			System.out.println("sign in userDetails - null " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("sign in userDetails - password not match " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	


	@Override
	public User findUserById(Long userId) {
		Optional<User> user=userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}
		ErrorModel errorModel = ErrorModel.builder()
				.code(CommonConstant.USER_NOT_FOUND_CODE)
				.message(CommonConstant.USER_NOT_FOUND)
				.timestamp(LocalDateTime.now())
				.build();
		throw new BusinessException(errorModel);
	}

	@Override
	public User findUserProfileByJwt(String jwt) {
		System.out.println("user service");
		String email=jwtTokenProvider.getEmailFromJwtToken(jwt);
		
		System.out.println("email"+email);
		
		Optional<User> user=userRepository.findByEmail(email);
		
		if(user.isEmpty()) {
			ErrorModel errorModel = ErrorModel.builder()
					.code(CommonConstant.USER_NOT_FOUND_CODE)
					.message(CommonConstant.USER_NOT_FOUND)
					.timestamp(LocalDateTime.now())
					.build();
			throw new BusinessException(errorModel);
		}
//		System.out.println("email user"+user.getEmail());
		return user.get();
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAllByOrderByCreatedAtDesc();
	}
@Override
	public void saveUserVerificationToken(User theUser, String token) {
		var verificationToken = new VerificationToken(token, theUser);
		tokenRepository.save(verificationToken);
	}
@Override
	public String validateToken(String theToken) {
		VerificationToken token = tokenRepository.findByToken(theToken);
		if(token == null){
			return CommonConstant.TOKEN_NOT_VALID;
		}
		User user = token.getUser();
		Calendar calendar = Calendar.getInstance();
		if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
			tokenRepository.delete(token);
			return CommonConstant.TOKEN_ALREADY_EXPIRED;
		}
		user.setEnabled(true);
		userRepository.save(user);
		return CommonConstant.TOKEN_VALID;
	}
@Override
	public VerificationToken findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

}
