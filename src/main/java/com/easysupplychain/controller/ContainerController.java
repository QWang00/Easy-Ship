package com.easysupplychain.controller;

import com.easysupplychain.entity.Container;
import com.easysupplychain.entity.Container;
import com.easysupplychain.entity.Port;
import com.easysupplychain.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/container/{id}")
    public String findContainer(@PathVariable Long id, Model model) {
        Container container = containerService.findContainerById(id);
        model.addAttribute("container", container);
        return "list-container";
    }
    @GetMapping("remove-container/{id}")
    public String removeContainer(@PathVariable Long id, Model model){
        containerService.deleteContainer(id);
        model.addAttribute("containers", containerService.findAllContainers());
        return "containers";
    }

    @PostMapping("update-container/{id}")
    public String saveUpdateContainer(@PathVariable Long id, Container container, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "update-container";
        containerService.updateContainer(container);
        model.addAttribute("containers", containerService.findAllContainers());
        return "redirect:/containers";
    }
    @GetMapping("update-container/{id}")
    public String updateContainer(@PathVariable Long id, Model model){
        model.addAttribute("container", containerService.findContainerById(id));
        return "update-container";
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
