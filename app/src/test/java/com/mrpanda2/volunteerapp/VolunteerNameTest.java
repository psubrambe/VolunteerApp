package com.mrpanda2.volunteerapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VolunteerNameTest {


        @Test
        public void setAndGetName() {
            String input = "volname";
            String output;
            String expected = "volname";
            Volunteer volName = new Volunteer();
            volName.setName(input);
            output = volName.getName();
            assertEquals(expected, output);
        }
    }

