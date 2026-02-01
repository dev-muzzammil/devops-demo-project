package com.example.sample.Service;

import com.example.sample.DTO.Request.CartRequestDTO;
import com.example.sample.DTO.Response.CartResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CartService {

    CartResponseDTO addCart(CartRequestDTO requestDTO);

    Optional<CartResponseDTO> getCartById(Long id);

    List<CartResponseDTO> getAllCart();

    CartResponseDTO updateCart(Long id, CartRequestDTO requestDTO);

    void deleteCart(Long id);

}
