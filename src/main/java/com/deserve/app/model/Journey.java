package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Journey {
  private Station source;
  private Station destination = null;
  private JourneyType journeyType;

  public Optional<Station> getDestination() {
    return Optional.ofNullable(destination);
  }
}
