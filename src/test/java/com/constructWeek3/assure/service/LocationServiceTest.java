package com.constructWeek3.assure.service;

import com.constructWeek3.assure.entity.Location;
import com.constructWeek3.assure.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");
    }

    // ---- addLocation tests ----

    @Test
    void addLocation_validLocation_savesAndReturnsSuccessMessage() {
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        String result = locationService.addLocation(location);

        assertEquals("Location has been added", result);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void addLocation_newLocation_callsSaveOnce() {
        Location newLocation = new Location();
        newLocation.setName("Delhi");
        when(locationRepository.save(any(Location.class))).thenReturn(newLocation);

        String result = locationService.addLocation(newLocation);

        assertNotNull(result);
        verify(locationRepository, times(1)).save(newLocation);
    }

    // ---- getAllLocation tests ----

    @Test
    void getAllLocation_returnsListOfLocations() {
        List<Location> locations = List.of(location);
        when(locationRepository.findAll()).thenReturn(locations);

        List<Location> result = locationService.getAllLocation();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mumbai", result.get(0).getName());
    }

    @Test
    void getAllLocation_emptyList_returnsEmptyList() {
        when(locationRepository.findAll()).thenReturn(new ArrayList<>());

        List<Location> result = locationService.getAllLocation();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllLocation_multipleLocations_returnsAll() {
        Location location2 = new Location();
        location2.setLocationId(2L);
        location2.setName("Delhi");

        when(locationRepository.findAll()).thenReturn(List.of(location, location2));

        List<Location> result = locationService.getAllLocation();

        assertEquals(2, result.size());
    }
}
