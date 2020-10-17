package com.deserve.app;

import com.deserve.app.model.Journey;

public interface FareCalculator {
  double getFare(Journey journey);
}
