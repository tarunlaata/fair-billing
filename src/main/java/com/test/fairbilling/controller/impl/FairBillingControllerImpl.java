package com.test.fairbilling.controller.impl;

import com.test.fairbilling.controller.FairBillingController;
import com.test.fairbilling.service.FairBillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
public class FairBillingControllerImpl implements FairBillingController {
    private static final Logger log = LoggerFactory.getLogger(FairBillingControllerImpl.class);
    @Autowired
    FairBillingService fairBillingService;

    @GetMapping("/")
    public String index() {
        return "fairBillingApp";
    }

    @GetMapping("/fairBillingApp")
    public Map<String, String> getSessionData(@RequestParam String filePath) throws IOException {
        //filePath = "C:\\Users\\TALA0219\\Desktop\\test.log";
        log.info("filePath: {}", filePath);
        return fairBillingService.getSessionData(filePath);
    }
}
