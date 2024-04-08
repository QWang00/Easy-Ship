package com.easysupplychain.controller;

import com.easysupplychain.entity.*;
import com.easysupplychain.entity.Payment;
import java.util.ArrayList;
import java.util.Optional;
import com.easysupplychain.service.ContainerService;
import com.easysupplychain.service.ForwarderService;
import com.easysupplychain.service.PaymentService;
import com.easysupplychain.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ContainerService containerService;
    @Autowired
    private ShipperService shipperService;
    @Autowired
    private ForwarderService forwarderService;

    @GetMapping("/payments")
    public String findAllPayments(Model model) {
        List<Payment> payments = paymentService.findAllPayments();
        model.addAttribute("payments", payments);
        return "payments";
    }

    @GetMapping("/payment/{id}")
    public String findPayment(@PathVariable Long id, Model model) {
        Payment payment = paymentService.findPaymentById(id);
        model.addAttribute("payment", payment);
        return "list-payment";
    }

    @GetMapping("remove-payment/{id}")
    public String removePayment(@PathVariable Long id, Model model){
        paymentService.deletePayment(id);
        model.addAttribute("payments", paymentService.findAllPayments());
        return "payments";
    }

    @GetMapping("update-payment/{id}")
    public String updatePayment(@PathVariable Long id, Model model){
        Payment payment = paymentService.findPaymentById(id);
        model.addAttribute("payment", payment);
        addAttributesToModel(model, payment);
        return "update-payment";
    }

    @PostMapping("save-updatePayment/{id}")
    public String saveUpdatePayment(@ModelAttribute Payment payment, BindingResult bindingResult,
                                    // Added a new parameter to directly receive the PaymentRecipient's ID from the form
                                    @RequestParam("paymentRecipientId") Long recipientId, Model model) {
        if (bindingResult.hasErrors()) {
            // Add the necessary attributes again so that they are available in the model
            addAttributesToModel(model, payment);
            // Return to the form
            return "update-payment";
        }

        // Initialize recipient to null
        PaymentRecipient recipient = null;

        Optional<Shipper> shipperOpt = shipperService.findShipperByIdOptional(recipientId);

        // If not found, then attempt to find as a Forwarder
        if (!shipperOpt.isPresent()) {
            recipient = forwarderService.findForwarderById(recipientId);
        } else {
            recipient = shipperOpt.get();
        }

        // Check if a valid recipient was found
        if (recipient == null) {
            model.addAttribute("error", "Invalid recipient selected.");
            return "update-payment";
        }

        // Fetch the selected container
        Container selectedContainer = containerService.findContainerById(payment.getContainer().getId());
        if (selectedContainer == null) {
            model.addAttribute("error", "Invalid container selected.");
            return "update-payment";
        }

        // Validate the selected container matches the recipient type (shipper/forwarder)
        if (!validateRecipientAndContainer(recipient, selectedContainer)) {
            model.addAttribute("recipientContainerError", "Selected container and recipient do not match.");
            addAttributesToModel(model, payment);
            return "update-payment";
        }

        // Manually set the recipient in the Payment object since it was fetched using ID
        payment.setPaymentRecipient(recipient);

        paymentService.updatePayment(payment);
        return "redirect:/payments";

    }


    @GetMapping("/add-payment")
    public String showCreatePayment(Payment payment, Model model) {
        List<PaymentRecipient> recipients = new ArrayList<>();
        recipients.addAll(shipperService.findAllShippers());
        recipients.addAll(forwarderService.findAllForwarders());
        model.addAttribute("recipients", recipients);
        model.addAttribute("containers", containerService.findAllContainers());
        return "add-payment";
    }

    @PostMapping("/save-payment")
    public String savePayment(@ModelAttribute Payment payment, BindingResult bindingResult,
                              @RequestParam("paymentRecipientId") Long recipientId, Model model) {
        if (bindingResult.hasErrors()) {
            addAttributesToModel(model, payment);
            return "add-payment";
        }

        // Initialize recipient to null
        PaymentRecipient recipient = null;

        Optional<Shipper> shipperOpt = shipperService.findShipperByIdOptional(recipientId);

        // If not found, then attempt to find as a Forwarder
        if (!shipperOpt.isPresent()) {
            recipient = forwarderService.findForwarderById(recipientId);
        } else {
            recipient = shipperOpt.get();
        }

        // Check if a valid recipient was found
        if (recipient == null) {
            model.addAttribute("error", "Invalid recipient selected.");
            return "add-payment";
        }

        // Fetch the selected container
        Container selectedContainer = containerService.findContainerById(payment.getContainer().getId());
        if (selectedContainer == null) {
            model.addAttribute("error", "Invalid container selected.");
            return "add-payment";
        }

        // Validate the selected container matches the recipient type (shipper/forwarder)
        if (!validateRecipientAndContainer(recipient, selectedContainer)) {
            model.addAttribute("recipientContainerError", "Selected container and recipient do not match.");
            addAttributesToModel(model, payment);
            return "add-payment";
        }

        // Manually set the recipient in the Payment object since it was fetched using ID
        payment.setPaymentRecipient(recipient);

        // Save the payment
        paymentService.createPayment(payment);
        return "redirect:/payments";
    }

    // Utility method for adding common attributes to the model
    private void addAttributesToModel(Model model, Payment payment) {
        model.addAttribute("payment", payment);
        List<PaymentRecipient> recipients = new ArrayList<>();
        recipients.addAll(shipperService.findAllShippers());
        recipients.addAll(forwarderService.findAllForwarders());
        model.addAttribute("recipients", recipients);
        model.addAttribute("containers", containerService.findAllContainers());

    }

    // Utility method for validating if the selected recipient is the selected container's shipper/forwarder
    private boolean validateRecipientAndContainer(PaymentRecipient recipient, Container selectedContainer) {
        // Check if the recipient is a shipper and if the selected container contains this shipper
        if (recipient instanceof Shipper && selectedContainer.getShippers().contains(recipient)) {
            return true;
        }

        // Check if the recipient is a forwarder and if the forwarder matches the container's forwarder
        return recipient instanceof Forwarder && recipient.equals(selectedContainer.getForwarder());
    }





}