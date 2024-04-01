package com.easysupplychain.repository;

import com.easysupplychain.entity.PaymentRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PaymentRecipientRepository<T extends PaymentRecipient> extends JpaRepository<T, Long> {
    // Common methods for both shippers and forwarders
}
