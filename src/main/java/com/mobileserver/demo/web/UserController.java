package com.mobileserver.demo.web;

import com.mobileserver.demo.domain.Book;
import com.mobileserver.demo.domain.Token;
import com.mobileserver.demo.domain.User;
import com.mobileserver.demo.service.BookService;
import com.mobileserver.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/greeting")
    public ResponseEntity<List<Book>> greeting() {
        return new ResponseEntity<List<Book>>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginDto loginDto) {
        Optional<String> token = userService.signin(loginDto.getUsername(), loginDto.getPassword());
        if (token.isPresent()) {
            LOGGER.info("User successfully authenticated");
            return new ResponseEntity<Token>(new Token(token.get()), HttpStatus.OK);
        } else throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed");
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerErrorException(HttpServerErrorException ex, HttpServletResponse res) throws IOException {
        res.sendError(ex.getStatusCode().value(), ex.getMessage());
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@RequestBody @Valid LoginDto loginDto) {
        return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),
                loginDto.getLastName()).orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
    }

}