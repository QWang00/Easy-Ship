package com.easysupplychain.repository;

import com.easysupplychain.entity.PaymentRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecipientRepository extends JpaRepository<PaymentRecipient, Long> {
}
