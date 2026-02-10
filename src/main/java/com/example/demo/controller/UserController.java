package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequestest;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users/")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public List<UserDTO> getAll(){
        return userService.findAll();
    }
    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody User user) throws Exception{
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
   public ResponseEntity<UserDTO>delete(@PathVariable UUID id){
    userService.delete(id);
    return ResponseEntity.noContent().build();
   } 
   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody LoginRequestest loginRequest){
     try {
        UserDTO userDto = userService.login(loginRequest.getTaxId(), loginRequest.getPassword());
        return ResponseEntity.ok(userDto);
     } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED). body(Map.of("error", "login failed", "message", e.getMessage()));
     } 
   }
}
