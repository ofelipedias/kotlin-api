package com.spring.kotlin.example.controller

import com.spring.kotlin.example.exception.ServiceException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun <T> handleNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<Response<T>> {
        val errors = exception.bindingResult.allErrors.stream()
        val apiErrors = errors
                .map { e -> e.defaultMessage }
                .collect(Collectors.toList())
        val response: Response<T> = Response(apiErrors as ArrayList<String>)
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(ServiceException::class)
    fun <T> handleServiceException(exception: ServiceException): ResponseEntity<Response<T>> {
        val response = Response<T>()
        response.errors.add(exception.message!!)
        return ResponseEntity.badRequest().body(response)
    }
}