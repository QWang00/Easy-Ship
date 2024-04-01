package com.easysupplychain.controller;

import com.easysupplychain.entity.Forwarder;
import com.easysupplychain.service.ForwarderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ForwarderController {

    @Autowired
    private ForwarderService forwarderService;

    @GetMapping("/forwarders")
    public String findAllForwarders(Model model) {
        List<Forwarder> forwarders = forwarderService.findAllForwarders();
        model.addAttribute("forwarders", forwarders);
        return "forwarders";
    }

    @GetMapping("/add-forwarder")
    public String showCreateForwarder(Forwarder forwarder) {
        return "add-forwarder";
    }

    @PostMapping("/save-forwarder")
    public String saveForwarder(Forwarder forwarder, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-forwarder";
        forwarderService.createForwarder(forwarder);
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
        return "redirect:/forwarders";
    }

}