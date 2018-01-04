package com.bootifulmicropizza.service.inventory.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class MoneySerializer extends JsonSerializer<Money> {

    @Override
    public void serialize(Money money, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(money.getAmount().setScale(2, ROUND_HALF_EVEN));
    }
}
