package com.example.sample.ServiceImpl;

import com.example.sample.DTO.UserRequestDTO;
import com.example.sample.DTO.UserResponseDTO;
import com.example.sample.Entity.User;
import com.example.sample.Exception.ResourceNotFoundException;
import com.example.sample.Mapper.UserMapper;
import com.example.sample.Repo.UserRepo;
import com.example.sample.Service.UserService;
import com.example.sample.Validation.Admin;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO requestDTO) {
        if (userRepo.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepo.existsByContactNo(requestDTO.getContactNo())) {
            throw new IllegalArgumentException("Contact number already registered");
        }

        User user = userMapper.toEntity(requestDTO);

        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    public UserResponseDTO getById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    @Admin
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO requestDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + id));

        // Check for duplicate email/contact number during update
        if (userRepo.existsByEmailAndIdNot(requestDTO.getEmail(), id)) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepo.existsByContactNoAndIdNot(requestDTO.getContactNo(), id)) {
            throw new IllegalArgumentException("Contact number already registered");
        }

        userMapper.updateFromRequest(requestDTO, user);
        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    @Admin
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with Id: " + id);
        }
        userRepo.deleteById(id);
    }


}
