package com.mrpanda2.volunteerapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrgNameTest {
    @Test
    public void setAndGetName() {
        String input = "orgname";
        String output;
        String expected = "orgname";
        Organization volName = new Organization();
        volName.setName(input);
        output = volName.getName();
        assertEquals(expected, output);
    }
}
