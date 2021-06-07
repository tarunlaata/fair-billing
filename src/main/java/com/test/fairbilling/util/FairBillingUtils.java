package com.test.fairbilling.util;

import com.test.fairbilling.model.InputData;
import com.test.fairbilling.model.SessionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.test.fairbilling.util.FairBillingConstants.START;
import static java.time.temporal.ChronoUnit.SECONDS;

public class FairBillingUtils {

    private FairBillingUtils() { }

    static List<InputData> inputDataList = new ArrayList<>();
    static List<SessionData> sessionDataList = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(FairBillingUtils.class);


    public static List<InputData> modelInputData(String filePath) throws IOException {
        List<String> listString = Files.readAllLines(Paths.get(filePath));
        for (String string : listString) {
            String[] splitString = string.split(" ");
            if (LocalTime.parse(splitString[0]) != null && (splitString[2].endsWith(START) || splitString[2].endsWith(FairBillingConstants.END))) {
                InputData input = new InputData();
                input.setTime(LocalTime.parse(splitString[0]));
                input.setName(splitString[1]);
                input.setEvent(splitString[2]);
                inputDataList.add(input);
            }
        }
        return inputDataList;
    }

    public static LocalTime getSpecificTime(int i, List<InputData> inputDataList) {
        InputData inputFirst = inputDataList.get(i);
        LocalTime specificTime = inputFirst.getTime();
        String logTime = (i == 0) ? "/Start Time" : "/End Time";
        log.info("{} : {}", logTime , specificTime);
        return specificTime;
    }

    public static List<SessionData> modelSessionData(List<InputData> inputDataList) {
        LocalTime startTime = getSpecificTime(0, inputDataList);
        LocalTime endTime = getSpecificTime(inputDataList.size() - 1, inputDataList);
        for (InputData input : inputDataList) {
            SessionData sessionData = new SessionData();
            sessionData.setCacheTime(new TreeMap<>());
            if (sessionDataList.parallelStream().anyMatch(o -> o.getName().equals(input.getName()))) {
                sessionData = sessionDataList.parallelStream().filter(o -> o.getName().equals(input.getName())).findFirst().get();
                boolean isCacheSessionEmpty = sessionData.getCacheTime().isEmpty();
                sessionData.setSessionCount(sessionData.getSessionCount() + 0.5);
                sessionData.setLastEvent(input.getEvent());
                if (input.getEvent().equals(START)) {
                    sessionData.getCacheTime().put(isCacheSessionEmpty ? 1 : sessionData.getCacheTime().lastKey() + 1, input.getTime());
                } else if (input.getEvent().equals(FairBillingConstants.END)) {
                    sessionData.setTotalTime(sessionData.getTotalTime() + (SECONDS.between(isCacheSessionEmpty ? startTime : sessionData.getCacheTime().get(sessionData.getCacheTime().firstKey()), input.getTime())));
                    if (!isCacheSessionEmpty) {
                        sessionData.getCacheTime().remove(sessionData.getCacheTime().firstKey());
                    }
                }
            } else {
                sessionData.setName(input.getName());
                sessionData.setLastEvent(input.getEvent());
                if (input.getEvent().equals(START)) {
                    sessionData.setSessionCount(0.5);
                } else if (input.getEvent().equals(FairBillingConstants.END)) {
                    sessionData.setSessionCount(1);
                    sessionData.setTotalTime(SECONDS.between(startTime, input.getTime()));
                }
                sessionDataList.add(sessionData);
            }
        }
        for (SessionData sessionData: sessionDataList) {
            if(sessionData.getSessionCount() % 1 != 0) {
                sessionData.setSessionCount(sessionData.getSessionCount() + 0.5);
            }
            if (sessionData.getLastEvent().equals(START)) {
                sessionData.setTotalTime(sessionData.getTotalTime() + SECONDS.between(sessionData.getCacheTime().get(sessionData.getCacheTime().firstKey()), endTime));
            }
        }
        return sessionDataList;
    }
}
