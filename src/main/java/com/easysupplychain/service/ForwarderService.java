package com.easysupplychain.service;

import com.easysupplychain.entity.Forwarder;
import com.easysupplychain.repository.ForwarderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ForwarderService {
    @Autowired
    private ForwarderRepository forwarderRepository;

    public List<Forwarder> findAllForwarders() {
        return forwarderRepository.findAll();
    }

    public Optional<Forwarder> findForwarderByIdOptional(Long id) {
        return forwarderRepository.findById(id);
    }

    public Forwarder findForwarderById(Long id) {
        Forwarder forwarder = forwarderRepository.findById(id).orElseThrow(() ->new RuntimeException("Forwarder Not found"));
        return forwarder;
    }

    public void createForwarder(Forwarder forwarder){
        forwarderRepository.save(forwarder);
    }

    public void updateForwarder(Forwarder forwarder){
        forwarderRepository.save(forwarder);
    }

    public void deleteForwarder(Long id){
        Forwarder forwarder = forwarderRepository.findById(id).orElseThrow(()-> new RuntimeException("Forwarder Not Found"));
        forwarderRepository.deleteById(forwarder.getId());
    }
}
