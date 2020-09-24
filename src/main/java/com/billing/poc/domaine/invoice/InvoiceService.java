package com.billing.poc.domaine.invoice;

import com.billing.poc.domaine.invoice.model.Invoice;
import com.billing.poc.infra.sql.InvoiceRepository;
import com.billing.poc.interfaces.http.model.InvoiceDTO;
import com.billing.poc.interfaces.http.model.request.CreateInvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceDTO saveData(CreateInvoiceRequest createInvoiceRequest) {

        Invoice invoice = Invoice.builder()
                .created(LocalDateTime.now())
                .date(createInvoiceRequest.getDate())
                .number(createInvoiceRequest.getNumber())
                .paid(false)
                .totalAmount(createInvoiceRequest.getTotalAmount())
                .build();
        return invoiceRepository.save(invoice).toDto();
    }
}
