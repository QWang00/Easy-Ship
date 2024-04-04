package com.easysupplychain.service;


import com.easysupplychain.entity.Container;
import com.easysupplychain.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContainerService {
    @Autowired
    private ContainerRepository portRepository;

    public List<Container> findAllContainers() {
        return portRepository.findAll();
    }

    public Container findContainerById(Long id) {
        Container container = portRepository.findById(id).orElseThrow(() ->new RuntimeException("Container Not found"));
        return container;
    }
    @Transactional
    public void createContainer(Container container){
        portRepository.save(container);
    }
    @Transactional
    public void updateContainer(Container container){
        portRepository.save(container);
    }
    @Transactional
    public void deleteContainer(Long id){
        Container container = portRepository.findById(id).orElseThrow(()-> new RuntimeException("Container Not Found"));
        portRepository.deleteById(container.getId());
    }
}
