package com.easysupplychain;

import com.easysupplychain.entity.*;
import com.easysupplychain.repository.CountryRepository;
import com.easysupplychain.repository.ForwarderRepository;
import com.easysupplychain.repository.PortRepository;
import com.easysupplychain.repository.ShipperRepository;
import com.easysupplychain.service.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class EasyShipApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyShipApplication.class, args);}

    @Bean

    public CommandLineRunner loadData(CountryService countryService, PortService portService, ShipperService shipperService,
                                      ForwarderService forwarderService, PaymentService paymentService, ContainerService containerService
                                      ) {
        return (args) -> {
            Country country1 = new Country("UK");
            Country country2 = new Country("China");
            Country country3 = new Country("Belgium");
            Country country4 = new Country("Indonesia");

            Port port1 = new Port("Southampton");
            Port port2 = new Port("Felixstowe");
            Port port3 = new Port("Ningbo");
            Port port4 = new Port("Yantian");
            Port port5 = new Port("Xiamen");
            Port port6 = new Port("Antwerp");
            Port port7 = new Port("Jakarta");
            port1.setCountry(country1);
            port2.setCountry(country1);
            port3.setCountry(country2);
            port4.setCountry(country2);
            port5.setCountry(country2);
            port6.setCountry(country3);
            port7.setCountry(country4);

            LocalDate date1 = LocalDate.of(2023, 4, 26);
            LocalDate date2 = LocalDate.of(2023, 7, 20);

            Payment payment1 = new Payment("Shu123", new BigDecimal("45000.00"), date1, "PO0032123");
            Payment payment2 = new Payment("Sch123", new BigDecimal("1600.00"), date2, "Local Charge");

            Forwarder forwarder1 = new Forwarder("Schenker", "GBP", "End of Following Month");
            Forwarder forwarder2 = new Forwarder("Robema", "EUR", "7 Days Before ETA");
            payment2.setPaymentRecipient(forwarder1);

            Shipper shipper1 = new Shipper("Shuree", "USD", "7 Days Before ETA");
            Shipper shipper2 = new Shipper("Wentee", "USD", "7 Days Before ETA");
            shipper1.setClosestPort(port4);
            shipper2.setClosestPort(port3);
            shipper1.addPayment(payment1);

            Container container1 = new Container("HQWE1230123", "40HQ", 1000, new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-21"), new SimpleDateFormat("yyyy-MM-dd").parse("2021-03-29"));
            container1.setForwarder(forwarder1);
            container1.addShipperToContainer(container1, shipper1);
            container1.setToPort(port1);
            container1.setFromPort(shipper1.getClosestPort());

            countryService.createCountry(country1);
            countryService.createCountry(country2);
            countryService.createCountry(country3);
            countryService.createCountry(country4);
            portService.createPort(port1);
            portService.createPort(port2);
            portService.createPort(port3);
            portService.createPort(port4);
            portService.createPort(port5);
            portService.createPort(port6);
            portService.createPort(port7);
            forwarderService.createForwarder(forwarder1);
            forwarderService.createForwarder(forwarder2);

            shipperService.createShipper(shipper1);
            shipperService.createShipper(shipper2);
            paymentService.createPayment(payment1);
            paymentService.createPayment(payment2);

            containerService.createContainer(container1);

        };}}













