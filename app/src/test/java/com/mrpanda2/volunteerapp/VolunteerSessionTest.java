package com.mrpanda2.volunteerapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class VolunteerSessionTest {

    @Test
    public void setAndGetEventName() {
        String input = "Test";
        String output;
        String expected = "Test";

        VolunteerSession volSess = new VolunteerSession();
        volSess.setEventName(input);
        output = volSess.getEventName();
        assertEquals(expected, output);

    }
}