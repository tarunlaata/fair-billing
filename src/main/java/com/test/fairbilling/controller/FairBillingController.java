package com.test.fairbilling.controller;

import java.io.IOException;
import java.util.Map;

public interface FairBillingController {

    String index();

    Map<String, String> getSessionData(String filePath) throws IOException;
}