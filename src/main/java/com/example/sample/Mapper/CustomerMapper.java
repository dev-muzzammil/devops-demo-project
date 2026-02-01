package com.example.sample.Mapper;

import com.example.sample.DTO.Request.CustomerRequestDTO;
import com.example.sample.DTO.Response.CustomerResponseDTO;
import com.example.sample.Entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO toDTO(Customer customer);

    @Mapping(target = "id" , ignore = true)
    Customer toEntity(CustomerRequestDTO requestDTO);

}
