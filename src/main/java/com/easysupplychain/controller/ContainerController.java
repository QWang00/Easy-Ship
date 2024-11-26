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
    public String showUpdateContainerForm(@PathVariable Long id, Model model) {
        Container container = containerService.findContainerById(id);
        addAttributesToModel(model, container);
        return "update-container";
    }

    @PostMapping("save-updateContainer/{id}")
    public String updateContainer(@PathVariable Long id,
                                  @ModelAttribute @Valid Container container,
                                  BindingResult bindingResult,
                                  @RequestParam(required = false) List<Long> shipperIds,
                                  Model model) {
        String attribute = validateBeforeSave(bindingResult, model, container, "update-container", shipperIds);
        if (attribute != null) return attribute;

        // Proceed with updating the container and associated shippers
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
        String attribute = validateBeforeSave(bindingResult, model, container, "add-container", shipperIds);
        if (attribute != null) return attribute;

        // Proceed with saving the container and associated shippers
        containerService.createContainer(container, shipperIds);
        return "redirect:/containers";
    }

    // Utility method for adding common attributes to the model
    private void addAttributesToModel(Model model, Container container) {
        model.addAttribute("container", container);
        model.addAttribute("ports", portService.findAllPorts());
        model.addAttribute("shippers", shipperService.findAllShippers());
        model.addAttribute("forwarders", forwarderService.findAllForwarders());
    }

    // Utility method for validating shippers - if all shippers have the same port
    private boolean validateShippers(List<Long> shipperIds, Container container, Model model) {
        if (shipperIds == null || shipperIds.isEmpty()) {
            model.addAttribute("shipperSelectionError", "At least one shipper must be selected.");
            addAttributesToModel(model, container);
            return false;
        }

        List<Shipper> selectedShippers = shipperService.findAllShippersByIds(shipperIds);
        boolean validShippers = selectedShippers.stream()
                .allMatch(shipper -> shipper.getClosestPort().equals(container.getFromPort()));

        if (!validShippers) {
            model.addAttribute("shipperPortError", "All selected shippers must have the same closest port as the container's departure port.");
            addAttributesToModel(model, container);
            return false;
        }

        return true;
    }

    private String validateBeforeSave(BindingResult bindingResult, Model model, Container container, String attribute, List<Long> shipperIds) {
        if (bindingResult.hasErrors()) {
            addAttributesToModel(model, container);
            return attribute;
        }

        // Validation for ETD and ETA
        if (container.getETD() != null && container.getETA() != null && !container.getETD().before(container.getETA())) {
            model.addAttribute("dateTimeError", "ETD must be earlier than ETA.");
            addAttributesToModel(model, container);
            return attribute;
        }

        // Validate shipper selection and departure port
        if (!validateShippers(shipperIds, container, model)) {
            // `validateShippers` will add necessary attributes to the model if there's an error
            return attribute;
        }
        return null;
    }
}


