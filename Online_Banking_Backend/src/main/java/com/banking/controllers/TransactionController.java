package com.banking.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entities.Transaction;
import com.banking.services.EmailService;
import com.banking.services.MonthlyStatementPdfService;
import com.banking.services.TransactionService;
import com.banking.services.YearlyStatementPdfService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.banking.dtos.MonthlyStatementResponse;
import com.banking.dtos.TransactionResponse;
import com.banking.dtos.YearlyStatementResponse;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
	
    private final TransactionService transactionService;
    private final MonthlyStatementPdfService monthlyStatementPdfService;
    private final YearlyStatementPdfService yearlyStatementPdfService;
    private final EmailService emailService;
    
    @GetMapping
    public Page<TransactionResponse> getTransactions(
            Authentication authentication,
            @RequestParam String accountNumber,
            @RequestParam(required = false) String type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate,
            Pageable pageable
    ) {

        return transactionService.getTransactions(
                authentication.getName(),
                accountNumber,
                type,
                startDate,
                endDate,
                pageable
        );
    }
    //monthly statement
    @GetMapping("/statement/monthly")
    public MonthlyStatementResponse getMonthlyStatement(
            Authentication authentication,
            @RequestParam String accountNumber,
            @RequestParam int month,
            @RequestParam int year
    ) {
        return transactionService.getMonthlyStatement(
                authentication.getName(),
                accountNumber,
                month,
                year
        );
    }
    //monthly statement pdf 
    @GetMapping("/monthly/pdf")
    public ResponseEntity<byte[]> downloadMonthlyPdf(
            @RequestParam String accountNumber,
            @RequestParam int month,
            @RequestParam int year,
            Authentication authentication
    ) {

        MonthlyStatementResponse statement =
                transactionService.getMonthlyStatement(
                        authentication.getName(),
                        accountNumber,
                        month,
                        year
                );

        byte[] pdf = monthlyStatementPdfService.generatePdf(statement);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=statement.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
    //yearly statement
    @GetMapping("/statement/yearly")
    public YearlyStatementResponse getYearlyStatement(
    		@AuthenticationPrincipal UserDetails userDetails,
    		@RequestParam String accountNumber,
    		@RequestParam int year
    		) {
    	return transactionService.getYearlyStatement(
    			userDetails.getUsername(),
    			accountNumber,
    			year
    			);
    }
    //yearly statement pdf
    @GetMapping("/yearly/pdf")
    public ResponseEntity<byte[]>downloadYearlyStatementPdf(
    		@AuthenticationPrincipal UserDetails userDetails,
    		@RequestParam String accountNumber,
    		@RequestParam int year
    		
    		){
    	//Secure-username checked inside service
    	YearlyStatementResponse statement=
    			transactionService.getYearlyStatement(
    					userDetails.getUsername(), 
    					accountNumber, 
    					year
    					);
    	//Generate pdf
    	byte[]pdf=yearlyStatementPdfService.generatePdf(statement);
    	
    	//Return as downlodable file
    	return ResponseEntity.ok()
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=yearly-statement-"+year+".pdf")
    			.contentType(MediaType.APPLICATION_PDF)
    			.body(pdf);
    }
    
    //email service
    //yearly 
    @GetMapping("/yearly/pdf/email")
    public ResponseEntity<String>emailYearlyStatement(
    		@AuthenticationPrincipal UserDetails user,
    		@RequestParam(name="accountNumber") String accountNumber,
    		@RequestParam int year
    		){
    	YearlyStatementResponse statement=
    			transactionService.getYearlyStatement(user.getUsername(), accountNumber, year);
    	
    	byte[] pdf=yearlyStatementPdfService.generatePdf(statement);
    	
    	emailService.sendPdf(
    			"your_email@gmail.com",
    			"Yearly Statement "+year,
    			"Please find your statement attached",
    			pdf,
    			"Yearly_Statement_"+year+".pdf"
    			);
    	return ResponseEntity.ok("Email sent successfully");
    }
    //monthly email service
    @GetMapping("/monthly/pdf/email")
    public ResponseEntity<String>emailMonthlyStatement(
    		@AuthenticationPrincipal UserDetails user,
    		@RequestParam String accountNumber,
    		@RequestParam int month,
    		@RequestParam int year
    		){
    			MonthlyStatementResponse statement=
    					transactionService.getMonthlyStatement(
    							user.getUsername(),
    							accountNumber,
    							month,
    							year);
    			byte[] pdf=monthlyStatementPdfService.generatePdf(statement);
    			
    			emailService.sendPdf(
    					"your_email@gmail.com",
    					"Monthly Statement"+month+"/"+year,
    					"please find your monthly statement attached",
    					pdf,
    					"monthly_Statement_"+month+"_"+year+".pdf"
    					);
    			return ResponseEntity.ok("Monthly statement sent successfully");
    }
}
