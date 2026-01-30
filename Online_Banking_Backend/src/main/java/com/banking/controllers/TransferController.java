package com.banking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dtos.TransferRequest;
import com.banking.services.TransferService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransferRequest request
    ) {
        transferService.transferMoney(
                userDetails.getUsername(),
                request
        );
        return ResponseEntity.ok("Transfer successful");
    }
}
