package com.czajor.rxjavatutorial;

import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@ToString
public
class Trackpoint {
    private final BigDecimal lat;
    private final BigDecimal lon;
}
