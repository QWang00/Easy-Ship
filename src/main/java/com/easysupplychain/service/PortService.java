package com.easysupplychain.service;


import com.easysupplychain.entity.Port;
import com.easysupplychain.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class PortService {
    @Autowired
    private PortRepository portRepository;

    public List<Port> findAllPorts() {
        return portRepository.findAll();
    }

    public Port findPortById(Long id) {
        Port port = portRepository.findById(id).orElseThrow(() ->new RuntimeException("Port Not found"));
        return port;
    }

    public void createPort(Port port){
        portRepository.save(port);
    }

    public void updatePort(Port port){
        portRepository.save(port);
    }

    public void deletePort(Long id){
        Port port = portRepository.findById(id).orElseThrow(()-> new RuntimeException("Port Not Found"));
        portRepository.deleteById(port.getId());
    }
}
