package com.banking.scheduler;

import java.time.YearMonth;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.banking.dtos.MonthlyStatementResponse;
import com.banking.entities.Account;
import com.banking.entities.User;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.UserRepository;
import com.banking.services.EmailService;
import com.banking.services.MonthlyStatementPdfService;
import com.banking.services.TransactionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyStatementScheduler {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final TransactionService transactionService;
	private final MonthlyStatementPdfService monthlyPdfService;
	private final EmailService emailService;
	
	//Runs at 1st day  of every month at 10AM
	@Scheduled(cron="0 0 10 1 * ?")
	public void sendMonthlyStatements() {
		 System.out.println("Running Monthly Statement Scheduler...");
		 
		 //Last month
		 YearMonth lastMonth = YearMonth.now().minusMonths(1);
		 
		 int month = lastMonth.getMonthValue();
		 int year = lastMonth.getYear();
		 
		 List<User> users=userRepository.findAll();
		 
		 for(User user:users) {
			 List<Account>accounts=accountRepository.findByUserUsername(user.getUsername());
			 
			 for(Account account : accounts) {
				 try {
					 MonthlyStatementResponse statement=
							 transactionService.getMonthlyStatement(user.getUsername(),
									 account.getAccountNumber(),
									 month,
									 year
									 );
					 byte[] pdf=monthlyPdfService.generatePdf(statement);
					 
					 emailService.sendPdf(
							 user.getEmail(),
							 "Monthly Statement"+month+"/"+year,
							 "Dear"+user.getUsername()+", your statement is attached.",
							 pdf,
							 "Monthly_Statement_"+month+"_"+year+".pdf"
							 );
					 
					 System.out.println("Sent to: "+user.getEmail());
				 }catch(Exception e) {
					 System.out.println("Failed for: "+user.getUsername());
				 }
			 }
		 }
	}
}
