package com.billing.poc.infra.sql;

import com.billing.poc.domaine.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository  extends JpaRepository<Invoice, Integer>, com.billing.poc.domaine.invoice.InvoiceRepository {

   @Override
    default Invoice saveInvoice(Invoice invoice) {
       return save(invoice);
   }
}
