package com.billing.poc.domaine.invoice.model;

import com.billing.poc.interfaces.http.model.response.InvoiceDTO;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    private String name;

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
                .name(this.name)
                .paid(this.paid)
                .paidDate(this.paidDate)
                .reminderLevel(this.reminderLevel)
                .totalAmount(this.totalAmount)
                .build();
    }
}
