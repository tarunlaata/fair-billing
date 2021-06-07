package com.test.fairbilling.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.test.fairbilling.model.InputData;
import com.test.fairbilling.model.SessionData;
import com.test.fairbilling.service.FairBillingService;
import com.test.fairbilling.util.FairBillingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class FairBillingServiceImpl implements FairBillingService {
    List<InputData> inputDataList = new ArrayList<>();
    List<SessionData> sessionDataList = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(FairBillingServiceImpl.class);

    @Override
    public Map<String, String> getSessionData(String filePath) throws IOException {
        log.info("filePath : {}", filePath);
        inputDataList = FairBillingUtils.modelInputData(filePath);
        inputDataList.sort(Comparator.comparing(InputData::getTime));
        inputDataList.sort(Comparator.comparing(InputData::getName));
        log.info("Input Data :");
        inputDataList.forEach(id ->log.info(id.toString()));
        sessionDataList = FairBillingUtils.modelSessionData(inputDataList);
        log.info("Session Data :");
        sessionDataList.forEach(sd ->log.info(sd.toString()));

        Gson gson = new Gson();
        String input = gson.toJson(inputDataList);
        Gson gson1 = new Gson();
        String session = gson1.toJson(sessionDataList);
        return Collections.singletonMap(input, session);
    }
}
