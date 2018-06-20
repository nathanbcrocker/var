package com.digiup.algo.var.events;

import com.digiup.algo.var.domain.ValueAtRisk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class VaRCalculationComplete {
    @Getter @NonNull
    private ValueAtRisk valueAtRisk;
}
