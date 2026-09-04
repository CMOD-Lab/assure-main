package com.constructWeek3.assure.controller;

import com.constructWeek3.assure.entity.Location;
import com.constructWeek3.assure.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");
    }

    // ---- addLocation tests ----

    @Test
    void addLocation_validLocation_returnsSuccessMessageWithOkStatus() {
        when(locationService.addLocation(location)).thenReturn("Location has been added");

        ResponseEntity<String> response = locationController.addLocation(location);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Location has been added", response.getBody());
        verify(locationService, times(1)).addLocation(location);
    }

    @Test
    void addLocation_newLocation_callsServiceOnce() {
        Location newLocation = new Location();
        newLocation.setName("Delhi");
        when(locationService.addLocation(newLocation)).thenReturn("Location has been added");

        ResponseEntity<String> response = locationController.addLocation(newLocation);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ---- getAllLocation tests ----

    @Test
    void getAllLocation_returnsListOfLocations() {
        List<Location> locations = List.of(location);
        when(locationService.getAllLocation()).thenReturn(locations);

        List<Location> result = locationController.getAllLocation();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mumbai", result.get(0).getName());
    }

    @Test
    void getAllLocation_emptyList_returnsEmptyList() {
        when(locationService.getAllLocation()).thenReturn(new ArrayList<>());

        List<Location> result = locationController.getAllLocation();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllLocation_multipleLocations_returnsAll() {
        Location location2 = new Location();
        location2.setLocationId(2L);
        location2.setName("Delhi");

        when(locationService.getAllLocation()).thenReturn(List.of(location, location2));

        List<Location> result = locationController.getAllLocation();

        assertEquals(2, result.size());
    }
}
