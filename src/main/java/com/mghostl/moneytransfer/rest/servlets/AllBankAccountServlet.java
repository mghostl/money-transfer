package com.mghostl.moneytransfer.rest.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllBankAccountServlet extends BankAccountServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllBankAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("Received getAllBankAccountsRequest");
        resp.setStatus(HttpServletResponse.SC_OK);
        try {
            resp.getWriter().print(bankAccountService.getAllBankAccounts());
        } catch (Exception e) {
            LOGGER.error("Couldn't send response due to: {}", e);
        }
    }
}
