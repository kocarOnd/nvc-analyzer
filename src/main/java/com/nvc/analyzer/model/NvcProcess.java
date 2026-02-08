package com.nvc.analyzer.model;

import java.util.UUID;

public class NvcProcess {
    private String id;
    private String observation;
    private String feeling;
    private String need;
    private String request;

    public NvcProcess() {
        this.id = UUID.randomUUID().toString();
        this.observation = "";
        this.feeling = "";
        this.need = "";
        this.request = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "NVC Process Summary:\n" +
               "1. When I see/hear: " + observation + "\n" +
               "2. I feel: " + feeling + "\n" +
               "3. Because I need: " + need + "\n" +
               "4. Would you be willing to: " + request;
    }
}