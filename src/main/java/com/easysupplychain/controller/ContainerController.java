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
    public String updateContainer(@PathVariable Long id, Model model) {
        Container container = containerService.findContainerById(id);
        model.addAttribute("container", container);
        model.addAttribute("ports", portService.findAllPorts());
        model.addAttribute("shippers", shipperService.findAllShippers());
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
        return "update-container";
    }

    @PostMapping("save-updateContainer/{id}")
    public String saveUpdateContainer(@PathVariable Long id, Container container, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "update-container";
        containerService.updateContainer(container);
        model.addAttribute("containers", containerService.findAllContainers());
        return "redirect:/containers";
    }



    @GetMapping("/add-container")
    public String showCreateContainer(Container container, Model model) {
        model.addAttribute("ports", portService.findAllPorts());
        model.addAttribute("shippers", shipperService.findAllShippers());
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
        return "add-container";
    }


    @PostMapping("/save-container")
    public String saveContainer(@ModelAttribute @Valid Container container, BindingResult bindingResult, @RequestParam List<Long> shipperIds, Model model) {
        if (bindingResult.hasErrors()) {
            // Your existing error handling code
            return "add-container";
        }
        // Validation for ETD and ETA
        if (container.getETD() != null && container.getETA() != null && !container.getETD().before(container.getETA())) {
            model.addAttribute("dateTimeError", "ETD must be earlier than ETA.");
            model.addAttribute("ports", portService.findAllPorts());
            model.addAttribute("shippers", shipperService.findAllShippers());
            model.addAttribute("forwarders", forwarderService.findAllForwarders());
            return "add-container";
        }
        // Fetch the selected shippers based on IDs

        List<Shipper> selectedShippers = shipperService.findAllShippersByIds(shipperIds);


        // Validate that all selected shippers have the same closest port as the container's departure port
        boolean validShippers = selectedShippers.stream()
                .allMatch(shipper -> shipper.getClosestPort().equals(container.getFromPort()));

        if (!validShippers) {
            // Add a custom error message to the model
            model.addAttribute("shipperPortError", "All selected shippers must have the same closest port as the container's departure port.");

            // Repopulate the model attributes needed for the form
            model.addAttribute("container", container);
            model.addAttribute("ports", portService.findAllPorts());
            model.addAttribute("shippers", shipperService.findAllShippers());
            model.addAttribute("forwarders", forwarderService.findAllForwarders());

            // Return the user to the "add-container" view
            return "add-container";
        }

        // Proceed with saving the container and associated shippers
        containerService.createOrUpdateContainer(container, shipperIds);
        return "redirect:/containers";
    }



}


