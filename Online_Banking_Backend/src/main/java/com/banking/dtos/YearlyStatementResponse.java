package com.banking.dtos;

import java.util.List;

public class YearlyStatementResponse {

    private String accountNumber;
    private int year;
    private Double openingBalance;
    private Double closingBalance;
    private Double totalCredit;
    private Double totalDebit;
    private List<TransactionResponse> transactions;

    public YearlyStatementResponse(
            String accountNumber,
            int year,
            Double openingBalance,
            Double closingBalance,
            Double totalCredit,
            Double totalDebit,
            List<TransactionResponse> transactions
    ) {
        this.accountNumber = accountNumber;
        this.year = year;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.totalCredit = totalCredit;
        this.totalDebit = totalDebit;
        this.transactions = transactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getYear() {
        return year;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public Double getTotalCredit() {
        return totalCredit;
    }

    public Double getTotalDebit() {
        return totalDebit;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }
}
