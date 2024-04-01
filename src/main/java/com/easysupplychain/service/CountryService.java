package com.easysupplychain.service;

import com.easysupplychain.entity.Country;
import com.easysupplychain.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public Country findCountryById(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(() ->new RuntimeException("Country Not found"));
        return country;
    }

    public void createCountry(Country country){
        countryRepository.save(country);
    }

    public void updateCountry(Country country){
        countryRepository.save(country);
    }

    public void deleteCountry(Long id){
        Country country = countryRepository.findById(id).orElseThrow(()-> new RuntimeException("Country Not Found"));
        countryRepository.deleteById(country.getId());
    }
}
