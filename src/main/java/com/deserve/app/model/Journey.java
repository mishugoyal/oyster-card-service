package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Journey {
  private Station source;
  private Optional<Station> destination;
  private JourneyType journeyType;
}
