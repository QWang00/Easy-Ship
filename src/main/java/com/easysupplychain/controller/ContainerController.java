package com.easysupplychain.controller;

import com.easysupplychain.entity.Container;
import com.easysupplychain.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    @GetMapping("/containers")
    public String findAllContainers(Model model) {
        List<Container> containers = containerService.findAllContainers();
        model.addAttribute("containers", containers);
        return "containers";
    }

    @GetMapping("/add-container")
    public String showCreateContainer(Container container) {
        return "add-container";
    }

    @PostMapping("/save-container")
    public String saveContainer(Container container, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-container";
        containerService.createContainer(container);
        model.addAttribute("containers", containerService.findAllContainers());
        return "redirect:/containers";
    }

}
