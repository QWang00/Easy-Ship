package com.easysupplychain.controller;
import com.easysupplychain.entity.Container;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.service.ContainerService;
import com.easysupplychain.service.ForwarderService;
import com.easysupplychain.service.PortService;
import com.easysupplychain.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
public class ContainerController {

    @Autowired
    private ContainerService containerService;
    @Autowired
    private PortService portService;
    @Autowired
    private ShipperService shipperService;
    @Autowired
    private ForwarderService forwarderService;


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
    public String removeContainer(@PathVariable Long id, Model model) {
        containerService.deleteContainerWithShipper(id);
        model.addAttribute("containers", containerService.findAllContainers());
        return "containers";
    }

    @GetMapping("update-container/{id}")
    public String showUpdateContainerForm(@PathVariable Long id, Model model) {
        Container container = containerService.findContainerById(id);
        populateModelAttributes(model, container);
        return "update-container";
    }

    @PostMapping("save-updateContainer/{id}")
    public String updateContainer(@PathVariable Long id,
                                  @ModelAttribute @Valid Container container,
                                  BindingResult bindingResult,
                                  @RequestParam(required = false) List<Long> shipperIds,
                                  Model model) {
        String dateTimeError = containerService.validateEtdAndEta(container);

        if (dateTimeError != null) {
            model.addAttribute("dateTimeError", dateTimeError);
            populateModelAttributes(model, container);
            return "update-container";
        }

        // Validate Shippers
        Map<String, String> shipperErrors = containerService.validateShippers(shipperIds, container);
        if (!shipperErrors.isEmpty()) {
            shipperErrors.forEach(model::addAttribute);
            populateModelAttributes(model, container);
            return "update-container";
        }

        containerService.updateContainer(container, shipperIds);
        return "redirect:/containers";
    }

    @GetMapping("/add-container")
    public String showCreateContainerForm(Container container, Model model) {
        model.addAttribute("ports", portService.findAllPorts());
        model.addAttribute("shippers", shipperService.findAllShippers());
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
        return "add-container";
    }

    @PostMapping("/save-container")
    public String createContainer(@ModelAttribute @Valid Container container,
                                BindingResult bindingResult,
                                @RequestParam(required = false) List<Long> shipperIds,
                                Model model) {
        if (bindingResult.hasErrors()) {
            populateModelAttributes(model, container);
            return "add-container";
        }

        // Validate ETD and ETA
        String dateTimeError = containerService.validateEtdAndEta(container);
        if (dateTimeError != null) {
            model.addAttribute("dateTimeError", dateTimeError);
            populateModelAttributes(model, container);
            return "add-container";
        }

        // Validate Shippers
        Map<String, String> shipperErrors = containerService.validateShippers(shipperIds, container);
        if (!shipperErrors.isEmpty()) {
            shipperErrors.forEach(model::addAttribute);
            populateModelAttributes(model, container);
            return "add-container";
        }

        containerService.createContainer(container, shipperIds);
        return "redirect:/containers";
    }

    // Utility method for adding common attributes to the model
    private void populateModelAttributes(Model model, Container container) {
        model.addAttribute("container", container);
        model.addAttribute("ports", portService.findAllPorts());
        model.addAttribute("shippers", shipperService.findAllShippers());
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
    }



}


