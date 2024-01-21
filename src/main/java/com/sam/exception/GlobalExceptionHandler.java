package com.sam.exception;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;




@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorModel>> handleFieldValidation(MethodArgumentNotValidException manv){

		List<ErrorModel> errorModelList = new ArrayList<>();
		ErrorModel errorModel = null;
		List<FieldError> fieldErrorList = manv.getBindingResult().getFieldErrors();

		for(FieldError fe: fieldErrorList){
			logger.debug("Inside field validation: {} - {}", fe.getField(), fe.getDefaultMessage());
			logger.info("Inside field validation: {} - {}", fe.getField(), fe.getDefaultMessage());
			errorModel = new ErrorModel();
			errorModel.setCode(fe.getField());
			errorModel.setMessage(fe.getDefaultMessage());
			errorModelList.add(errorModel);
		}

		return new ResponseEntity<List<ErrorModel>>(errorModelList, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorModel> handleBusinessException(BusinessException bex){
		ErrorModel errorModel = bex.getErrors();
		logger.debug("BusinessException is thrown - level- debug: {} - {}", errorModel.getCode(), errorModel.getMessage());
		logger.info("BusinessException is thrown - level- info: {} - {}", errorModel.getCode(), errorModel.getMessage());
		logger.warn("BusinessException is thrown - level-warn: {} - {}", errorModel.getCode(), errorModel.getMessage());
		logger.error("BusinessException is thrown - level-error: {} - {}", errorModel.getCode(), errorModel.getMessage());
		return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);

	}


}
