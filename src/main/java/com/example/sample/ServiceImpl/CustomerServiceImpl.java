package com.example.sample.ServiceImpl;

import com.example.sample.DTO.CustomerRequestDTO;
import com.example.sample.DTO.CustomerResponseDTO;
import com.example.sample.Entity.Customer;
import com.example.sample.Mapper.CustomerMapper;
import com.example.sample.Repo.CustomerRepo;
import com.example.sample.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        Customer savedCustomer = (customerMapper.toEntity(requestDTO));
        return customerMapper.toDTO(customerRepo.save(savedCustomer));
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        return customerMapper.toDTO(customerRepo.save(customer));
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (!customerRepo.existsById(id)){
            throw new RuntimeException("Customer not found with id " + id);
        }
        customerRepo.deleteById(id);
    }

}
