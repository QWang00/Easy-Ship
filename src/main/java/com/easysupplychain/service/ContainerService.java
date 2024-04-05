package com.easysupplychain.service;


import com.easysupplychain.entity.Container;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.repository.ContainerRepository;
import com.easysupplychain.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContainerService {
    @Autowired
    private ContainerRepository containerRepository;
    @Autowired
    ShipperRepository shipperRepository;

    public List<Container> findAllContainers() {
        return containerRepository.findAll();
    }

    public Container findContainerById(Long id) {
        Container container = containerRepository.findById(id).orElseThrow(() ->new RuntimeException("Container Not found"));
        return container;
    }
    @Transactional
    public void createContainer(Container container){
        containerRepository.save(container);
    }
    @Transactional
    public void updateContainer(Container container){
        containerRepository.save(container);
    }
    @Transactional
    public void deleteContainer(Long id){
        Container container = containerRepository.findById(id).orElseThrow(()-> new RuntimeException("Container Not Found"));
        containerRepository.deleteById(container.getId());
    }
    @Transactional
    public void createOrUpdateContainer(Container container, List<Long> shipperIds) {
        // If it's an update, load the existing container to manage relationships correctly
        if (container.getId() != null) {
            Container existingContainer = containerRepository.findById(container.getId())
                    .orElseThrow(() -> new RuntimeException("Container Not found"));
            existingContainer.getShippers().clear(); // Clear current shippers if you're replacing them
        }

        // Find shippers from the database
        List<Shipper> shippers = shipperRepository.findAllById(shipperIds);

        // Use the addShipper method to establish the relationship correctly
        shippers.forEach(container::addShipper);

        // Save the container, which will also persist the associations due to cascading
        containerRepository.save(container);
    }

    @Transactional
    public void deleteContainerWithShipper(Long id){
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Container Not Found"));

        // Copy the shippers to avoid ConcurrentModificationException during iteration
        Set<Shipper> shippersCopy = new HashSet<>(container.getShippers());

        // Use the removeShipper method to disassociate each shipper
        for (Shipper shipper : shippersCopy) {
            container.removeShipper(shipper);
            // No need to save each shipper here if you are using cascading persist on the relationship,
            // as the save at the end of this method should propagate changes.
        }

        // Save the container after shipper removal to ensure changes are cascaded
        containerRepository.save(container);

        // Now we can safely delete the container
        containerRepository.deleteById(container.getId());

        // Any post-deletion logic like logging or notifications
        // ...
    }


}
