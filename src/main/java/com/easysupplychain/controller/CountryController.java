package com.easysupplychain.controller;

import com.easysupplychain.entity.Country;
import com.easysupplychain.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public String findAllCountries(Model model) {
        List<Country> countries = countryService.findAllCountries();
        model.addAttribute("countries", countries);
        return "countries";
    }

    @GetMapping("/country/{id}")
    public String findCountry(@PathVariable Long id, Model model) {
        Country country = countryService.findCountryById(id);
        model.addAttribute("country", country);
        return "list-country";
    }

    @GetMapping("/remove-country/{id}")
    public String removeCountry(@PathVariable Long id, Model model){
        countryService.deleteCountry(id);
        model.addAttribute("countries", countryService.findAllCountries());
        return "countries";
    }

    @PostMapping("/update-country/{id}")
    public String saveUpdateCountry(@PathVariable Long id, Country country, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "update-country";
        countryService.updateCountry(country);
        model.addAttribute("countries", countryService.findAllCountries());
        return "redirect:/countries";
    }

    @GetMapping("/update-country/{id}")
    public String updateCountry(@PathVariable Long id, Model model){
        model.addAttribute("country", countryService.findCountryById(id));
        return "update-country";
    }

    @GetMapping("/add-country")
    public String showCreateCountry(Country country) {
        return "add-country";
    }

    @PostMapping("/save-country")
    public String saveCountry(Country country, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-country";
        countryService.createCountry(country);
        model.addAttribute("countries", countryService.findAllCountries());
        return "redirect:/countries";
    }

}