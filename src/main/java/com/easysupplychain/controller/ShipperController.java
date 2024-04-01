package com.easysupplychain.controller;

import com.easysupplychain.entity.Shipper;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.service.ShipperService;
import com.easysupplychain.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @GetMapping("/shippers")
    public String findAllShippers(Model model) {
        List<Shipper> shippers = shipperService.findAllShippers();
        model.addAttribute("shippers", shippers);
        return "shippers";
    }

    @GetMapping("/add-shipper")
    public String showCreateShipper(Shipper shipper) {
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