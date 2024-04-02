package com.easysupplychain;

import com.easysupplychain.entity.*;
import com.easysupplychain.repository.CountryRepository;
import com.easysupplychain.repository.ForwarderRepository;
import com.easysupplychain.repository.PortRepository;
import com.easysupplychain.repository.ShipperRepository;
import com.easysupplychain.service.ContainerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class EasyShipApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyShipApplication.class, args);}

    @Bean

    public CommandLineRunner loadData(CountryRepository countryRepository, PortRepository portRepository) {
        return (args) -> {
            // Assuming countries are pre-populated or checked here
            Country country1 = new Country("Country A");
            countryRepository.save(country1); // Persist country1

            Country country2 = new Country("Country B");
            countryRepository.save(country2); // Persist country2

            // Now creating ports and associating them with an existing country
//            Port port1 = new Port("Port X", country1);
//            portRepository.save(port1); // Persist port1 with country1 associated
//
//            Port port2 = new Port("Port Y", country2);
//            portRepository.save(port2); // Persist port2 with country2 associated
//
//            // Assuming you want to create more ports for country1
//            Port port3 = new Port("Port Z", country1);
//            portRepository.save(port3); // Persist port3 with country1 associated
        };}}



//                 Create and save Shippers

//                Shipper shipper1 = new Shipper("Shipper One", "USD","30 days", port1);
//                shipperRepository.save(shipper1);
//
//
//                Shipper shipper2 = new Shipper("Shipper Two", "EUR", "45 days", port1);
//                shipperRepository.save(shipper2);


                // Create and save Forwarders
//                Forwarder forwarder1 = new Forwarder(new HashSet<>());
//                forwarder1.setName("Forwarder One");
//                forwarder1.setCurrency("USD");
//                forwarder1.setPaymentTerm("30 days");
//                forwarderRepository.save(forwarder1);
//
//
//                Forwarder forwarder2 = new Forwarder(new HashSet<>());
//                forwarder2.setName("Forwarder Two");
//                forwarder2.setCurrency("GBP");
//                forwarder2.setPaymentTerm("60 days");
//                forwarderRepository.save(forwarder2);







