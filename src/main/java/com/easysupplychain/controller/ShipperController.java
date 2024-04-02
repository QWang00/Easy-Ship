package com.easysupplychain.controller;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.service.PortService;
import com.easysupplychain.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @Autowired
    private PortService portService;

    @GetMapping("/shipper/{id}")
    public String findShipper(@PathVariable Long id, Model model) {
        Shipper shipper = shipperService.findShipperById(id);
        model.addAttribute("shipper", shipper);
        return "list-shipper";
    }

    @GetMapping("/shippers")
    public String findAllShippers(Model model) {
        List<Shipper> shippers = shipperService.findAllShippers();
        model.addAttribute("shippers", shippers);
        return "shippers";
    }
    @GetMapping("remove-shipper/{id}")
    public String removeShipper(@PathVariable Long id, Model model){
        shipperService.deleteShipper(id);
        model.addAttribute("shippers", shipperService.findAllShippers());
        return "shippers";
    }

    @GetMapping("/update-shipper/{id}")
    public String updateShipper(@PathVariable Long id, Model model){
        Shipper shipper = shipperService.findShipperById(id);
        if (shipper == null) {
            return "redirect:/shippers";
        }

        model.addAttribute("shipper", shipper);
        model.addAttribute("ports", portService.findAllPorts());
        return "update-shipper";
    }

    @PostMapping("/save-Shipperupdate/{id}")
    public String saveUpdateShipper(@PathVariable Long id, Shipper shipper, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "update-shipper";
        }
        shipperService.updateShipper(shipper);
        model.addAttribute("shippers", shipperService.findAllShippers());
        return "redirect:/shippers";
    }

    @GetMapping("/add-shipper")
    public String showCreateShipper(Model model) {
        model.addAttribute("shipper", new Shipper());
        model.addAttribute("ports", portService.findAllPorts());
        return "add-shipper";
    }

    @PostMapping("/save-shipper")
    public String saveShipper(Shipper shipper, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-shipper";
        shipperService.createShipper(shipper);
        model.addAttribute("shippers", shipperService.findAllShippers());
        return "redirect:/shippers";
    }




}