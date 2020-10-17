package com.deserve.app.fare;

import com.deserve.app.model.Journey;

public interface FareCalculator {
  double getFare(Journey journey);
}
