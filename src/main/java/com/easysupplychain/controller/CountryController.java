package com.easysupplychain.controller;

import com.easysupplychain.entity.Country;
import com.easysupplychain.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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