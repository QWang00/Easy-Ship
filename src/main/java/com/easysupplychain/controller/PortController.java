package com.easysupplychain.controller;
import com.easysupplychain.entity.Port;
import com.easysupplychain.service.ContainerService;
import com.easysupplychain.service.CountryService;
import com.easysupplychain.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            return "redirect:/ports";
        }
        model.addAttribute("port", port);
        model.addAttribute("countries", countryService.findAllCountries());
        return "update-port";
    }


    @PostMapping("/save-update/{id}")
    public String saveUpdatePort(@PathVariable Long id,
                                 @ModelAttribute("port") Port updatedPort,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            model.addAttribute("countries", countryService.findAllCountries());
            return "update-port";
        }

        Port existingPort = portService.findPortById(id);
        if (existingPort == null) {
            return "redirect:/ports";
        }

        existingPort.setName(updatedPort.getName());
        existingPort.setCountry(updatedPort.getCountry());

        portService.updatePort(existingPort);
        model.addAttribute("ports", portService.findAllPorts());
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
            model.addAttribute("countries", countryService.findAllCountries());
            return "add-port";
        }

        portService.createPort(port);
        return "redirect:/ports";
    }

}