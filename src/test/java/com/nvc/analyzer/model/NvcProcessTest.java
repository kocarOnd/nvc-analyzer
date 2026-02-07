package com.nvc.analyzer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class NvcProcessTest {

    @Test
    void testNvcFlow() {
        NvcProcess process = new NvcProcess();

        process.setObservation("You left your socks on the floor.");
        process.setFeeling("annoyed");
        process.setNeed("order and cleanliness");
        process.setRequest("put them in the laundry basket");

        assertEquals("You left your socks on the floor.", process.getObservation());
        assertEquals("annoyed", process.getFeeling());
        assertNotNull(process.toString(), "The summary should not be null");
    }
}