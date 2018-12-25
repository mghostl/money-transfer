package com.mghostl.moneytransfer.rest.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class UpdateBalancesServlet extends BankAccountServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateBalancesServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("Received updateBalanceRequest");
        Map<String, String> params = getParamsMap(req);
        try {
            String fromAccount = params.get("fromAccount");
            String toAccount = params.get("toAccount");
            String amount = params.get("amount");
            if (fromAccount == null) {
                throw new IllegalArgumentException("Please provide fromAccount");
            }
            if (toAccount == null) {
                throw new IllegalArgumentException("Please provide toAccount");
            }
            if (amount == null) {
                throw new IllegalArgumentException("Please provide amount");
            }
            bankAccountService.updateBalances(fromAccount, toAccount, Double.valueOf(amount));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            String errorMessage = "Couldn't handle request due to: " + e.getMessage();
            LOGGER.error(errorMessage);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                resp.getWriter().print(errorMessage);
            }
            catch (Exception e1) {
                LOGGER.error("Couldn't send error message: {}", e1);
            }
        }
    }

}
