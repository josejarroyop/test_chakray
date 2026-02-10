package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.CryptoUtil;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<UserDTO>findAll(){
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public UserDTO create (User user) throws Exception{
        //if (user.getTaxId() != null) 
        if (userRepository.existsByTaxId(user.getTaxId())) throw new ResourceAlreadyExistsException("This tax id already registered");
        user.setName(user.getName());
        user.setPhone(user.getPhone());
        user.setPassword(CryptoUtil.encrypt(user.getPassword()));
        user.setTaxId(user.getTaxId());
        if (user.getAddresses()!= null) {
            user.getAddresses().forEach(address->address.setUser(user));
        }
    
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    private UserDTO convertToDTO (User user){
        UserDTO dto = new UserDTO();
        dto.setUuid(user.getUuid());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setTaxId(user.getTaxId());
        dto.setCreatedAt(user.getCreatedAt());
        if (user.getAddresses() != null) {
            List<AddressDTO> addressDTOs = user.getAddresses().stream().map( address -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setId(address.getId());
                addressDTO.setName(address.getName());
                addressDTO.setStreet(address.getStreet());
                addressDTO.setCountryCode(address.getCountryCode());
                return addressDTO;
            }).collect(Collectors.toList());
            dto.setAddresses(addressDTOs);
        }
        return dto;
    }
    public void delete(UUID id){
        if (!userRepository.existsByUuid(id)) throw new ResourceNotFoundException("User not found with this id");
        userRepository.deleteById(id);
    }
    public UserDTO login(String taxId, String password) throws Exception{
        User user = userRepository.findByTaxId(taxId).orElseThrow(() -> new Exception("Invalid tax_id or password"));
        //User user = userRepository.findById(taxId).orElseThrow(() -> new Exception("Invalid tax_id or password"));
        String decrptedPassword = CryptoUtil.decrypt(user.getPassword());
        if (!decrptedPassword.equals(password)) {
            throw new Exception("Invalid password");
        }
        return convertToDTO(user);
    }
    public List<UserDTO> findAllFiltered(String name, String taxId, String sortBy){
        String fieldToSort = switch (sortBy != null ? sortBy : "name"){
            case "email" -> "email";
            case "id" -> "id";
            case "name" -> "name";
            case "phone" -> "phone";
            case "tax_id" -> "tax_id";
            case "created_at" -> "created_at";
        }
    }
}
