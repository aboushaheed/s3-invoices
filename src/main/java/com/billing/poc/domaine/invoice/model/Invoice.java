package com.billing.poc.domaine.invoice.model;

import com.billing.poc.interfaces.http.model.InvoiceDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Invoice {
    @Id
    private Integer id;

    private String number;

    private BigDecimal totalAmount;

    private LocalDate date;

    private boolean paid = false;

    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    private ReminderLevel reminderLevel;

    private LocalDateTime created;


    public InvoiceDTO toDto() {
        return InvoiceDTO.builder()
                .created(this.created)
                .date(this.date)
                .number(this.number)
                .paid(this.paid)
                .paidDate(this.paidDate)
                .reminderLevel(this.reminderLevel)
                .totalAmount(this.totalAmount)
                .build();
    }
}
