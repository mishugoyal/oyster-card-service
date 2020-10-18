package com.deserve.app.fare;

import com.deserve.app.model.Journey;

import java.util.Optional;

public interface FareCalculator {
  Optional<Double> getFare(Journey journey);
}
