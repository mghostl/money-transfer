package com.mghostl.moneytransfer.rest.servlets;

import com.mghostl.moneytransfer.rest.BankAccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class BankAccountServlet extends HttpServlet {
    BankAccountService bankAccountService;

    @Override
    public void init() {
        if (bankAccountService == null) {
            bankAccountService = (BankAccountService) getServletContext().getAttribute("bankAccount.service");
        }
    }

    Map<String, String> getParamsMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> params.put(key, value[0]));
        return params;
    }
}
