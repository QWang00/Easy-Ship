package com.easysupplychain.controller;

import com.easysupplychain.entity.Forwarder;
import com.easysupplychain.service.ForwarderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/forwarder/{id}")
    public String findForwarder(@PathVariable Long id, Model model) {
        Forwarder forwarder = forwarderService.findForwarderById(id);
        model.addAttribute("forwarder", forwarder);
        return "list-forwarder";
    }

    @GetMapping("remove-forwarder/{id}")
    public String removeForwarder(@PathVariable Long id, Model model){
        forwarderService.deleteForwarder(id);
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
        return "forwarders";
    }

    @GetMapping("update-forwarder/{id}")
    public String updateForwarder(@PathVariable Long id, Model model){
        model.addAttribute("forwarder", forwarderService.findForwarderById(id));
        return "update-forwarder";
    }
    @PostMapping("update-forwarder/{id}")
    public String saveUpdateForwarder(@PathVariable Long id, Forwarder forwarder, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "update-forwarder";
        forwarderService.updateForwarder(forwarder);
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
        return "redirect:/forwarders";
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