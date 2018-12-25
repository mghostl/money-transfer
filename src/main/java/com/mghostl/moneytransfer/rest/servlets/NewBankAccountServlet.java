package com.mghostl.moneytransfer.rest.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class NewBankAccountServlet extends BankAccountServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewBankAccountServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("Receive add new BankAccount request");
        Map<String, String> params = getParamsMap(req);
        String name = params.get("name");
        String balance = params.get("balance");
        try {
            if (name == null) {
                throw new IllegalArgumentException("Please provide name");
            }
            if (balance == null) {
                throw new IllegalArgumentException("Please provide balance");
            }
            bankAccountService.addBankAccount(name, Double.valueOf(balance));
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            LOGGER.error("Couldn't handle request due to: {}", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
