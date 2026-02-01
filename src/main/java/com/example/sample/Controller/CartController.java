package com.example.sample.Controller;

import com.example.sample.DTO.CartRequestDTO;
import com.example.sample.DTO.CartResponseDTO;
import com.example.sample.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDTO> addCart(@RequestBody CartRequestDTO requestDTO) {
        CartResponseDTO cartResponseDTO = cartService.addCart(requestDTO);
        return new ResponseEntity<>(cartResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CartResponseDTO>> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAllCart() {
        return ResponseEntity.ok(cartService.getAllCart());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> updateCart(@PathVariable Long id ,
                                                      @RequestBody CartRequestDTO requestDTO) {
        return ResponseEntity.ok(cartService.updateCart(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }


}
