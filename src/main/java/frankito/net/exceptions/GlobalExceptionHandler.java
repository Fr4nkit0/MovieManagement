package frankito.net.exceptions;

import frankito.net.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler ({
            Exception.class,
            ResourceNotFoundException.class,
            InvalidPasswordException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class

    })
    public ResponseEntity<ApiResponse> handleAllExceptions(Exception exception,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response){
        ZoneId zona=ZoneId.of("America/Argentina");
        LocalDateTime timeTamp=LocalDateTime.now(zona);
        if (exception instanceof ResourceNotFoundException resourceNotFoundException)
            return this.handleResourceNotFoundException(resourceNotFoundException,request,response,timeTamp);
        if (exception instanceof  HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException)
            return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException,request,response,timeTamp);
        if (exception instanceof InvalidPasswordException invalidPasswordException)
            return this.handleInvalidPasswordException(invalidPasswordException,request,response,timeTamp);
        if (exception instanceof  MethodArgumentTypeMismatchException methodArgumentTypeMismatchException)
            return this.handleIMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException,request,response,timeTamp);
        if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException)
            return this.handleIMethodArgumentNotValidException(methodArgumentNotValidException,request,response,timeTamp);
        if (exception instanceof  HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException)
            return this.handleHttpMediaTypeNotSupported(httpMediaTypeNotSupportedException,request,response,timeTamp);
        if (exception instanceof  HttpMessageNotReadableException httpMessageNotReadableException)
            return this.handleHttpMessageNotReadableException(httpMessageNotReadableException,request,response,timeTamp);
        return this.handleException(exception,request,response,timeTamp);
    }
    private ResponseEntity<ApiResponse> handleException (Exception exception,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response,
                                                         LocalDateTime timeTamp){
        int httCode=HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(httCode)
                .body(new ApiResponse(httCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "Oops! Something went wrong on our server. Please try again later.",
                        exception.getMessage(),
                        timeTamp,
                        null
                        ));

    }
    private  ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                         HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         LocalDateTime timeTamp){
        int httpCode= HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "I'm sorry, the requested information could not be found. " +
                                "Please check the URL or try another search.",
                        resourceNotFoundException.getMessage(),
                        timeTamp,
                        null
                        ));
    }
    private  ResponseEntity<ApiResponse> handleIMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                                HttpServletRequest request,
                                                                                HttpServletResponse response,
                                                                                LocalDateTime timeTamp){
        int httpCode= HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        List<ObjectError> errors=methodArgumentNotValidException.getAllErrors();
        List<String> details=errors.stream().map( error->{
            if(error instanceof FieldError fieldError){
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            }
            return error.getDefaultMessage();
        }).toList();


        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "The request contains invalid or incomplete parameters. " +
                                "Please verify and provide the required information before trying again.",
                        methodArgumentNotValidException.getMessage(),
                        timeTamp,
                        details
                ));
    }
    private  ResponseEntity<ApiResponse> handleIMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException,
                                                                         HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         LocalDateTime timeTamp){
        int httpCode= HttpStatus.BAD_REQUEST.value();
        Object valueRejected = methodArgumentTypeMismatchException.getValue();
        String propertyName=methodArgumentTypeMismatchException.getName();
        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "Invalid Request: The provided value "
                                + valueRejected + " does not have the expected type for the " + propertyName,
                        methodArgumentTypeMismatchException.getMessage(),
                        timeTamp,
                        null
                ));
    }
    private  ResponseEntity<ApiResponse> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException,
                                                                        HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        LocalDateTime timeTamp){
        int httpCode= HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "Invalid Password: The provided password does not meet the required criteria, "
                                + invalidPasswordException.getErrorDescription(),
                        invalidPasswordException.getMessage(),
                        timeTamp,
                        null
                ));
    }
    private ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException (HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
                                                                                      HttpServletRequest request,
                                                                                      HttpServletResponse response,
                                                                                      LocalDateTime timeTamp){
        int httpCode = HttpStatus.METHOD_NOT_ALLOWED.value();
        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "Oops! Method Not Allowed. Check the HTTP method of your request.",
                        httpRequestMethodNotSupportedException.getMessage(),
                        timeTamp,
                        null
                ));

    }
    private ResponseEntity<ApiResponse> handleHttpMediaTypeNotSupported (HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException,
                                                                                      HttpServletRequest request,
                                                                                      HttpServletResponse response,
                                                                                      LocalDateTime timeTamp){
        int httpCode = HttpStatus.METHOD_NOT_ALLOWED.value();
        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request. " +
                                "Supported media types are: " + httpMediaTypeNotSupportedException.getSupportedMediaTypes()
                                + " and you send: " + httpMediaTypeNotSupportedException.getContentType(),
                        httpMediaTypeNotSupportedException.getMessage(),
                        timeTamp,
                        null
                ));

    }

    private ResponseEntity<ApiResponse> handleHttpMessageNotReadableException (HttpMessageNotReadableException httpMessageNotReadableException,
                                                                         HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         LocalDateTime timeTamp){
        int httpCode = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(httpCode)
                .body(new ApiResponse(
                        httpCode,
                        request.getRequestURL().toString(),
                        request.getMethod(),
                        "Oops! Error reading the HTTP message body. " +
                                "Make sure the request is correctly formatted and contains valid data.",
                        httpMessageNotReadableException.getMessage(),
                        timeTamp,
                        null
                ));

    }




}
