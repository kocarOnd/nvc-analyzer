package com.nvc.analyzer.model;

import java.util.UUID;

/**
 * Represents the components of the NVC process
 * Basic components of non violent communication are observation, feeling,
 * need and request.
 * @version 1.0
 * @since 2026-02-17
 * @see <a href="https://en.wikipedia.org/wiki/Nonviolent_Communication">NVC on Wikipedia</a>
 */
public class NvcProcess {
    /**The unique identifier for this specific process entry */
    private String id;
    /**The objective description of what happened in the past */
    private String observation;
    /**The emotion experienced or associated with the observation */
    private String feeling;
    /**The requirement that is causing the feeling */
    private String need;
    /**A specific, positively-worded action requested to fulfill the need*/
    private String request;

    public NvcProcess() {
        this.id = UUID.randomUUID().toString();
        this.observation = "";
        this.feeling = "";
        this.need = "";
        this.request = "";
    }

    public String getId() { return id; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
    public String getFeeling() { return feeling; }
    public void setFeeling(String feeling) { this.feeling = feeling; }
    public String getNeed() { return need; }
    public void setNeed(String need) { this.need = need; }
    public String getRequest() { return request; }
    public void setRequest(String request) { this.request = request; }

    /**
     * Returns a formatted string representing the complete 4-step NVC process.
     * The output includes the observation, feeling, need, and request formatted
     * as a continuous sentence.
     * @return A multi-line string summarizing the NVC process.
     */
    @Override
    public String toString() {
        return "NVC Process Summary:\n" +
               "1. When I see/hear: " + observation + "\n" +
               "2. I feel: " + feeling + "\n" +
               "3. Because I need: " + need + "\n" +
               "4. Would you be willing to: " + request;
    }
}