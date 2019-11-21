package com.mrpanda2.volunteerapp;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetInvalidLocationTest {

    @Test
    public void getAddressLocation() {
        String input = "Not a real place";
        LatLng expected = null;
        MapsActivity map = new MapsActivity();
        LatLng actual = map.getAddressLocation(input);
        assertEquals(expected, actual);
    }
}