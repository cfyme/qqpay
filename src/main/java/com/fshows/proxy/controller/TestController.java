package com.fshows.proxy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    public void test1(HttpServletRequest req, HttpServletResponse response) throws Exception {

        System.out.println("==========test1========");

        String merId = req.getParameter("merId");
        String orderId = req.getParameter("orderId");
        String txnTime = req.getParameter("txnTime");


    }


}
