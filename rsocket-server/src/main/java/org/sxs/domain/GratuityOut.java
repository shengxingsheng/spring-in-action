package org.sxs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GratuityOut {
 
    private BigDecimal billTotal;
    private int percent;
    private BigDecimal gratuity;

    public GratuityOut(GratuityIn gratuity) {
        this.percent = gratuity.getPercent();
        this.billTotal = gratuity.getBillTotal();
        this.gratuity = gratuity.getBillTotal().multiply(BigDecimal.valueOf(this.percent/100.0));
    }
}