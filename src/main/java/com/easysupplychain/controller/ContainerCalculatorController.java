package com.easysupplychain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContainerCalculatorController {

    @GetMapping("/calculate-size")
    public String loadForm() {
        return "containerCalculator"; // Name of your HTML template
    }

    @PostMapping("/display-size")
    public String calculateContainerSize(@RequestParam("length") double length,
                                         @RequestParam("width") double width,
                                         @RequestParam("height") double height,
                                         @RequestParam("quantity") int quantity,
                                         Model model) {

        // Validate that dimensions and quantity are positive
        if(length <= 0 || width <= 0 || height <= 0 || quantity <= 0) {
            model.addAttribute("resultMessage", "Dimensions and quantity must be greater than zero.");
            return "containerCalculator";}

        double totalCbm = (length / 100) * (width / 100) * (height / 100) * quantity; // Convert cm to m and calculate

        totalCbm = Math.max(totalCbm, 2.0);
        String message;
        if (totalCbm < 15) {
            message = String.format("You can book a %.2f CBM LCL Shipment", totalCbm);
        } else if (totalCbm < 28) {
            message = "You can book a 20FT container";
        } else if (totalCbm < 58) {
            message = "You can book a 40HQ or 40FT container";
        } else if (totalCbm < 68) {
            message = "You can book a 40HQ container";
        } else {
            message = "Your cargo is too large for standard containers.";
        }

        model.addAttribute("resultMessage", message);
        return "containerCalculator"; // Redirect back to the form page with the result message
    }
}
