package com.test.fairbilling.service;

import java.io.IOException;
import java.util.Map;

public interface FairBillingService {

    Map<String, String> getSessionData(String filePath) throws IOException;
}
