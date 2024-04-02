package com.countryservice;

import com.countryservice.beans.Country;
import com.countryservice.controllers.CountryController;
import com.countryservice.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMackitoTests.class})
public class ControllerMackitoTests {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> myCountries;
    Country country;

    @Test
    @Order(1)
    public void test_getALLCountries() {
        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(myCountries);
        ResponseEntity<List<Country>> response = countryController.getCountries();

        assertEquals(HttpStatus.FOUND,response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById() {

        country = new Country(2, "USA", "Washington");
        int countryId = 2;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryById(countryId);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryId, response.getBody().getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";
        when(countryService.getCountryByName(countryName)).thenReturn(country);

        ResponseEntity<Country> response = countryController.getCountryByName(countryName);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryName, response.getBody().getCountryName());

    }

    @Test
    @Order(4)
    public void test_addCountry() {
        country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(country, response.getBody());
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        country = new Country(3, "Spain", "Madrid");
        int countryId = 3;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ResponseEntity<Country> response = countryController.updateCountry(countryId, country);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getId());
        assertEquals("Spain", response.getBody().getCountryName());
        assertEquals("Madrid", response.getBody().getCountryCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        country = new Country(3, "Spain", "Madris");
        int countryId = 3;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.deleteCountry(countryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
