package com.countryservice;

import com.countryservice.beans.Country;
import com.countryservice.repositories.CountryRepository;
import com.countryservice.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMackitoTests.class})
public class ServiceMackitoTests {

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryService countryService;

    @Test
    @Order(1)
    public void test_getAllCountries() {

        List<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));

        when(countryRepository.findAll()).thenReturn(myCountries);
        assertEquals(2,  countryService.getAllCountries().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById() {
        List<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        int countryId = 1;
        when(countryRepository.findAll()).thenReturn(myCountries);
       assertEquals(countryId, countryService.getCountryById(countryId).getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        List<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        String countryName ="India" ;
        when(countryRepository.findAll()).thenReturn(myCountries);
        assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        Country country = new Country(3, "Germany", "Berlin");
        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        Country country = new Country(3, "Germany", "Berlin");
        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.updateCountry(country));
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        Country country = new Country(3, "Germany", "Berlin");
        countryService.deleteCountry(country);
        verify(countryRepository, times(1)).delete(country);
    }
}
