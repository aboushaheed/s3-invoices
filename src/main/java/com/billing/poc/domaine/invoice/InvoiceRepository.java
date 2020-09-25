package com.billing.poc.domaine.invoice;

import com.billing.poc.domaine.invoice.model.Invoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {

    Optional<Invoice> findById(Integer id);
    List<Invoice> findByDate(LocalDate date);
    Invoice saveInvoice(Invoice invoice);
    void deleteById(Integer id);
}
