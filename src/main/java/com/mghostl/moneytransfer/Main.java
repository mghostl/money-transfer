package com.mghostl.moneytransfer;

import com.mghostl.moneytransfer.dao.BankAccountsManager;
import com.mghostl.moneytransfer.rest.BankAccountServerFactory;
import com.mghostl.moneytransfer.rest.BankAccountService;
import com.mghostl.moneytransfer.rest.servlets.AllBankAccountServlet;
import com.mghostl.moneytransfer.rest.servlets.NewBankAccountServlet;
import com.mghostl.moneytransfer.rest.servlets.UpdateBalancesServlet;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        BankAccountsManager bankAccountsManager = new BankAccountsManager();
        bankAccountsManager.addBankAccount("Lev", 500);
        bankAccountsManager.addBankAccount("Sarah", 125);
        bankAccountsManager.addBankAccount("Jar", 235.4);
        LOGGER.info("BankAccountsManager initialized");
        printBankAccountsManagerState(bankAccountsManager);

        BankAccountService bankAccountService = new BankAccountService(bankAccountsManager);
        BankAccountServerFactory.setAttribute("bankAccount.service" ,bankAccountService);
        BankAccountServerFactory.addServlet(AllBankAccountServlet.class, "/allBankAccounts");
        BankAccountServerFactory.addServlet(UpdateBalancesServlet.class, "/updateBalances");
        BankAccountServerFactory.addServlet(NewBankAccountServlet.class, "/addBankAccount");
        Server server = BankAccountServerFactory.getInstance();
        LOGGER.info("Server started");
        server.join();
    }

    @SuppressWarnings("unchecked")
    private static void printBankAccountsManagerState(BankAccountsManager bankAccountsManager) {
        LOGGER.info("print bankAccountManagerState: ");
        bankAccountsManager.listBankAccounts().forEach(bankAccount -> LOGGER.info(bankAccount.toString()));
    }
}
