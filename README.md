# Oyster card service
* **OysterCard** - An interface which allows :
    * Adding balance,
    * Swiping in to a station,
    * Swiping out of a station,
    * Querying balance,
    * Querying transaction history on the card

    An implementation is provided which stores all data/state of the card in memory for demonstration purposes. Other more realistic implementations could use a persistent data store on the card itself or proxy to a remote service for maintaining this state.
* **FareCalculator** - An interface which allows calculating the fare between two stations for a given type of journey (bus, tube etc.). An implementation for a ZoneBasedFareCalculator is provided which calculates fare prices based on the zones in which the source and destination station are present. It handles calculating the best possible fare when there are multiple possibilities (When the source and/or destination station are on the boundary of 2 zones.) A different implementation could be used for other ways to calculate fare (for example based on the distance between 2 stations.)

* **ZoneBasedFareRepository** - A class which abstracts out how/where zone based fare rules are specified and stored. It provides a clean API which can provide the fare between a starting zone and (optionally) a destination zone for a given type of journey. The provided example implementation hardcodes the rules behind the API. A more realistic implementation will load and process rules from a persistent backend/remote service.
