package com.mghostl.moneytransfer.rest;

import com.mghostl.moneytransfer.dao.BankAccount;
import com.mghostl.moneytransfer.dao.BankAccountsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BankAccountService {
    private final BankAccountsManager bankAccountsManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);

    public BankAccountService(BankAccountsManager bankAccountsManager) {
        this.bankAccountsManager = bankAccountsManager;
    }

    public void updateBalances(String fromAccount, String toAccount, double amount) {
        LOGGER.info("Move balance {} from {} to {}", amount, fromAccount, toAccount);
        bankAccountsManager.updateBalances(fromAccount, toAccount, amount);
    }

    @SuppressWarnings("unchecked")
    public List<BankAccount> getAllBankAccounts() {
        LOGGER.info("Print all BankAccounts");
        return (List<BankAccount>) bankAccountsManager.listBankAccounts();
    }

    public void addBankAccount(String name, double balance) {
        bankAccountsManager.addBankAccount(name, balance);
    }
}
