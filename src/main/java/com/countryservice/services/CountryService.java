package com.countryservice.services;

import com.restservices.countryservice.beans.Country;
import com.restservices.countryservice.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return  countryRepository.findAll();
    }

    public Country getCountryById(int id) {
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for(Country element : countries){
            if(element.getId() == id)
                country = element;
        }
        return country;
    }

    public Country getCountryByName(String countryName) {
       List<Country> countries =  countryRepository.findAll();
       Country country = null;
       for(Country element : countries ){
           if(element.getCountryName().equalsIgnoreCase(countryName))
               country = element;
       }
       return country;
    }

    public Country addCountry(Country country) {
        country.setId(getMaxId());
        countryRepository.save(country);
        return country;
    }

    public Country updateCountry(Country country) {
        countryRepository.save(country);
        return country;
    }

    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

    // Utility method to get max id
    public  int getMaxId() {
        return countryRepository.findAll().size() + 1;
    }
}
