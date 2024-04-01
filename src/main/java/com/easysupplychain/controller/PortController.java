package com.easysupplychain.controller;

import com.easysupplychain.entity.Port;
import com.easysupplychain.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PortController {

    @Autowired
    private PortService portService;

    @GetMapping("/ports")
    public String findAllPorts(Model model) {
        List<Port> ports = portService.findAllPorts();
        model.addAttribute("ports", ports);
        return "ports";
    }

    @GetMapping("/add-port")
    public String showCreatePort(Port port) {
        return "add-port";
    }

    @PostMapping("/save-port")
    public String savePort(Port port, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-port";
        portService.createPort(port);
        model.addAttribute("ports", portService.findAllPorts());
        return "redirect:/ports";
    }

}