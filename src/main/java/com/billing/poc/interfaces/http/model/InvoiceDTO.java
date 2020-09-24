package com.billing.poc.interfaces.http.model;

import com.billing.poc.domaine.invoice.model.ReminderLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@ApiModel(description =  "invoice data format")
public class InvoiceDTO {

    @ApiModelProperty(notes = "the number of the invoice", example = "INV12300")
    private String number;

    @ApiModelProperty(notes = "the invoice Amount in Euros", example = "123.33")
    private BigDecimal totalAmount;

    @ApiModelProperty(notes = "date of issue of the invoice", example = "23/02/2019")
    private LocalDate date;

    @ApiModelProperty(notes = "flag for payment", example = "false")
    private boolean paid;

    @ApiModelProperty(notes = "date of payment if done", example = "false")
    private LocalDate paidDate;

    @ApiModelProperty(notes = "Level remind for this invoice", example = "LEVEL_1")
    private ReminderLevel reminderLevel;

    @ApiModelProperty(notes = "creation date of the document")
    private LocalDateTime created;
}
