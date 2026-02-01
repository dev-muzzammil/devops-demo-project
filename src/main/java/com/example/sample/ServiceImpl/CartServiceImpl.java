package com.example.sample.ServiceImpl;

import com.example.sample.DTO.CartRequestDTO;
import com.example.sample.DTO.CartResponseDTO;
import com.example.sample.Entity.Cart;
import com.example.sample.Repo.CartRepo;
import com.example.sample.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    @Override
    public CartResponseDTO addCart(CartRequestDTO requestDTO) {
        Cart cart = Cart.builder()
                .quantity(requestDTO.getQuantity())
                .totalAmount(requestDTO.getTotalAmount())
                .build();
        cartRepo.save(cart);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .totalAmount(cart.getTotalAmount())
                .build();
    }

    @Override
    public Optional<CartResponseDTO> getCartById(Long id) {
        return cartRepo.findById(id)
                .map(cart -> CartResponseDTO.builder()
                        .id(cart.getId())
                        .quantity(cart.getQuantity())
                        .totalAmount(cart.getTotalAmount())
                        .build());
    }

    @Override
    public List<CartResponseDTO> getAllCart() {
        return cartRepo.findAll()
                .stream()
                .map(cart -> CartResponseDTO.builder()
                        .id(cart.getId())
                        .quantity(cart.getQuantity())
                        .totalAmount(cart.getTotalAmount())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CartResponseDTO updateCart(Long id, CartRequestDTO requestDTO) {
        return cartRepo.findById(id)
                .map(cart -> {
                    cart.setQuantity(requestDTO.getQuantity());
                    cart.setTotalAmount(requestDTO.getTotalAmount());

                    Cart updated = cartRepo.save(cart);

                    return CartResponseDTO.builder()
                            .id(updated.getId())
                            .quantity(updated.getQuantity())
                            .totalAmount(updated.getTotalAmount())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Cart not found with id " + id));
    }


    @Override
    public void deleteCart(Long id) {
        if (!cartRepo.existsById(id)){
            throw new RuntimeException("Cart not found with id " + id);
        }
        cartRepo.deleteById(id);
    }


}
