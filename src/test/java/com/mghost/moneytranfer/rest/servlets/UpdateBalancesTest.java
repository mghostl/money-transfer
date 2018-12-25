package com.mghost.moneytranfer.rest.servlets;


import com.mghostl.moneytransfer.dao.BankAccount;
import com.mghostl.moneytransfer.dao.BankAccountsManager;
import com.mghostl.moneytransfer.rest.BankAccountServerFactory;
import com.mghostl.moneytransfer.rest.BankAccountService;
import com.mghostl.moneytransfer.rest.servlets.UpdateBalancesServlet;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdateBalancesTest {

    private BankAccountsManager accountsManager;

    private Server server;

    private static final double EPSILON = 1e-10;

    @Before
    public void init() {
        accountsManager = new BankAccountsManager();
        BankAccountService bankAccountService = new BankAccountService(accountsManager);
        BankAccountServerFactory.setAttribute("bankAccount.service" , bankAccountService);
        BankAccountServerFactory.addServlet(UpdateBalancesServlet.class, "/updateBalances");
        server = BankAccountServerFactory.getInstance();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldUpdateBalances() throws Exception {
        String name1 = "Test";
        String name2 = "Test2";
        double balance1 = 2000;
        double balance2 = 3000;
        accountsManager.addBankAccount(name1, balance1);
        accountsManager.addBankAccount(name2, balance2);
        double delta = 500;

        HttpClient httpClient = new HttpClient(new SslContextFactory());
        httpClient.start();
        Request request = httpClient.POST(server.getURI().toString().concat("updateBalances"))
                .param("fromAccount", name1)
                .param("toAccount", name2)
                .param("amount", Double.toString(delta))
                ;

        request.send();

        List accounts = accountsManager.listBankAccounts();
        accounts.forEach(accountRes -> {
            BankAccount account = (BankAccount) accountRes;
            if(account.getName().equals(name1)) {
                assertEquals(balance1 - delta, account.getBalance(), EPSILON);
            }
            if(account.getName().equals(name2)) {
                assertEquals(balance2 + delta, account.getBalance(), EPSILON);
            }
        });
    }

    @After
    public void stop() throws Exception {
        server.stop();
    }
}
