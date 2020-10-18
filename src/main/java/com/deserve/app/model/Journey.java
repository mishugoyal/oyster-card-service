package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class Journey {
  private Station source;
  private Station destination = null;
  private JourneyType journeyType;

  public Optional<Station> getDestination() {
    return Optional.ofNullable(destination);
  }
}
