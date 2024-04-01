package com.easysupplychain.controller;

import com.easysupplychain.entity.Country;
import com.easysupplychain.entity.Port;
import com.easysupplychain.service.CountryService;
import com.easysupplychain.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PortController {

    @Autowired
    private PortService portService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/ports")
    public String findAllPorts(Model model) {
        List<Port> ports = portService.findAllPorts();
        model.addAttribute("ports", ports);
        return "ports";
    }

    @GetMapping("/port/{id}")
    public String findPort(@PathVariable Long id, Model model) {
        Port port = portService.findPortById(id);
        model.addAttribute("port", port);
        return "list-port";
    }
    @GetMapping("/remove-port/{id}")
    public String removePort(@PathVariable Long id, Model model){
        portService.deletePort(id);
        model.addAttribute("ports", portService.findAllPorts());
        return "ports";
    }


    @GetMapping("/update-port/{id}")
    public String updatePort(@PathVariable Long id, Model model){
        Port port = portService.findPortById(id);
        if (port == null) {
            // handle the case where a port with the given ID doesn't exist
            return "redirect:/ports";
        }
        List<Country> countries = countryService.findAllCountries();
        model.addAttribute("port", port);
        model.addAttribute("countries", countryService.findAllCountries());
        return "update-port";
    }

    @PostMapping("/update-port/{id}")
    public String saveUpdatePort(@PathVariable Long id, Port port, BindingResult bindingResult, Model model){
        if (port.getCountry() == null || port.getCountry().getId() == null) {
            bindingResult.rejectValue("country", "error.port", "Selecting a country is required.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("port", port); // Keep the entered port data
            model.addAttribute("countries", countryService.findAllCountries()); // Add countries again
            return "update-port";
        }
        portService.updatePort(port); // Update the port
        return "redirect:/ports";
    }



    @GetMapping("/add-port")
    public String showCreatePort(Model model) {
        model.addAttribute("port", new Port());
        model.addAttribute("countries", countryService.findAllCountries());
        return "add-port";
    }

    @PostMapping("/save-port")
    public String savePort(Port port, BindingResult bindingResult, Model model) {
        if (port.getCountry() == null || port.getCountry().getId() == null) {
            bindingResult.rejectValue("countries", "error.port", "Selecting a country is required.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countryService.findAllCountries()); // Make sure this method exists and returns a list of all countries
            return "add-port"; // Show the form again with validation errors
        }

        portService.createPort(port); // Save the port with its associated country
        return "redirect:/ports"; // Redirect to prevent duplicate submissions and show all ports
    }

}