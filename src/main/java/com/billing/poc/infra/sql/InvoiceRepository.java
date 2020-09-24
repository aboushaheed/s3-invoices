package com.billing.poc.infra.sql;

import com.billing.poc.domaine.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository  extends JpaRepository<Invoice, Integer>, com.billing.poc.domaine.invoice.InvoiceRepository {

    @Override
    default Invoice save(Invoice invoice){
        return save(invoice);
    }
}
