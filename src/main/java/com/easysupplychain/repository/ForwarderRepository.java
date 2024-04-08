package com.easysupplychain.repository;

import com.easysupplychain.entity.Forwarder;
import org.springframework.stereotype.Repository;

@Repository
public interface ForwarderRepository extends PaymentRecipientRepository<Forwarder> {

}
