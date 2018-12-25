package com.mghostl.moneytransfer.rest;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

public class BankAccountServerFactory {
    private static final ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private static final ResourceHandler resourceHandler = new ResourceHandler();
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServerFactory.class);

    private static Server instance;

    private BankAccountServerFactory() {}

    public static Server getInstance() {
        if(instance == null) {
            instance = createServer();
        }
        return instance;
    }

    public static void addServlet(HttpServlet servlet, String path) {
        servletContextHandler.addServlet(new ServletHolder(servlet), path);
    }

    public static void addServlet(Class<? extends Servlet> servlet, String path) {
        servletContextHandler.addServlet(servlet, path);
    }

    public static void setAttribute(String name, Object attribute) {
        servletContextHandler.setAttribute(name, attribute);
    }

    private static Server createServer() {
        HandlerList handlers = new HandlerList();
        servletContextHandler.setContextPath("/");

        Server server = new Server(8080);
        server.setHandler(handlers);

        handlers.setHandlers(new Handler[]{servletContextHandler, resourceHandler});

        try {
            server.start();
        } catch (Exception e) {
            LOGGER.error("Couldn't start server due to: {}", e);
        }
        return server;
    }

}
