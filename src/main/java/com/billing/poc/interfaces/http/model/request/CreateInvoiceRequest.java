package com.billing.poc.interfaces.http.model.request;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceRequest {


    @ApiModelProperty(notes = "the number of the invoice", example = "INV12300")
    @NotNull
    private String number;

    @ApiModelProperty(notes = "the invoice Amount in Euros", example = "123.33")
    @NotNull
    private BigDecimal totalAmount;

    @ApiModelProperty(notes = "date of issue of the invoice", example = "23/02/2019")
    @NotNull
    @JsonDeserialize(using= JsonLocalDateDeserializer.class)
    private LocalDate date;

}
