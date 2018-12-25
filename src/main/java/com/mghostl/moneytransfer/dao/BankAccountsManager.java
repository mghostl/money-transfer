package com.mghostl.moneytransfer.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class BankAccountsManager {

    private static SessionFactory sessionFactory = new Configuration()
            .addAnnotatedClass(BankAccount.class)
            .configure().buildSessionFactory();

    public void addBankAccount(String name, double amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        BankAccount bankAccount = new BankAccount(name, amount);

        session.save(bankAccount);
        transaction.commit();

        session.close();
    }

    public List listBankAccounts() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List bankAccounts = session.createQuery("FROM BANKACCOUNT").list();
        transaction.commit();
        session.close();
        return bankAccounts;
    }

    private void updateBankAccount(String name, double delta) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM BANKACCOUNT as b where b.name = :name ");
        query.setParameter("name", name);
        BankAccount bankAccount = (BankAccount) query.getSingleResult();
        bankAccount.setBalance(bankAccount.getBalance() + delta);
        transaction.commit();
        session.close();
    }

    public void updateBalances(String fromAccount, String toAccount, double amount) {
        updateBankAccount(fromAccount, -amount);
        updateBankAccount(toAccount, amount);
    }

}
