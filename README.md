# Viagogo Coding Challenge

## How to run

Clone the repository.
```
git clone https://github.com/lghimfus/vcc-vee.git
```
Execute the following command from the root folder of the project.<br/>
**NOTE** *If the command fails, you probably need to install the Java JDK 8 (Java SE Development Kit)*.
```
./gradlew run -q
```

## How to use
* Execute the run command in the console. `./gradlew run -q`
* Input the two integer coordinates. `(e.g 1, 2)`
* Hit return and the details of the five closest events to the input coordinates will be returned. 
* If the input is wrong, the user will be asked to input the coordinates again. The loop can be stopped with the input `q`.
### Examples
#### Correct input example
```
Please input coordinates: > 
-5, 7

Closest events to (-5,7):

Event 061 - $01.38, Distance 1
Event 044 - $03.35, Distance 1
Event 066 - $05.26, Distance 2
Event 047 - $03.81, Distance 2
Event 091 - $01.98, Distance 3
```
#### Wrong input and quit example
```
Please input coordinates: > 
-11, 0

Input coordinates were not in the required range. Coordinates values should be integers between -10 and 10. Input q to QUIT.,
Please input coordinates: > 
-4

Two coordinates are needed. Coordinates values should be integers between -10 and 10. Input q to QUIT.,
Please input coordinates: > 
q                           

Bye bye
```

## Built with 
* [Gradle](https://gradle.org/)

## Source tree

 * [src/main/java/com/lghimfus/vee](./src/main/java/com/lghimfus/vee)
   * [models](./src/main/java/com/lghimfus/vee/models)
     * [Event.java](./src/main/java/com/lghimfus/vee/models/Event.java)
     * [Location.java](./src/main/java/com/lghimfus/vee/models/Location.java)
     * [Ticket.java](./src/main/java/com/lghimfus/vee/models/Ticket.java)
   * [services](./src/main/java/com/lghimfus/vee/services)
     * [EventService.java](./src/main/java/com/lghimfus/vee/services/EventService.java)
     * [LocationService.java](./src/main/java/com/lghimfus/vee/services/LocationService.java)
     * [TicketService.java](./src/main/java/com/lghimfus/vee/services/TicketService.java)
   * [ViagogoEventsExplorer.java](./src/main/java/com/lghimfus/vee/ViagogoEventsExplorer.java)

## Q&A
### Assumptions
#### Generating random seed data
  * The coordinates of a location can only be integers. Therefore, for a grid in the range (-10, 10) for both Y and X axis, there will be 400 available locations in the "world".
  * The program generates between 100 and 150 of random events. The range can be changed in [ViagogoEventsExplorer.java](./src/main/java/com/lghimfus/vee/ViagogoEventsExplorer.java) at line 39 `List<Event> eventsList = EventService.generateEvents(locations, 100, 150)`. I also assumed there will always exist at least one event.
  * The program generates between 0 and 50 tickets per event. The maximum number of tickets per event can be changed in [TicketService.java](./src/main/java/com/lghimfus/vee/services/TicketService.java) at line 23 `public static final int MAX_TICKETS_N0 = 50`
  * The price of one ticket is a random double value between 1 and 50. The range can be changed in [TicketService.java](./src/main/java/com/lghimfus/vee/services/TicketService.java) at line 24 `public static final double MIN_TICKET_PRICE = 1`, respectively at line 25 `public static final double MAX_TICKET_PRICE = 50`
  * If an event has zero tickets, then the program cannot find and print the cheapest ticket. Therefore, "no tickets were found" is printed instead of the ticket's price. The message can be changed in [ViagogoEventsExplorer.java](./src/main/java/com/lghimfus/vee/ViagogoEventsExplorer.java) at line 62 `ticketFormat = "no tickets were found"`

### How might you change your program if you needed to support multiple events at the same location?
  * At the moment, when an event is created, the method `getRandomAvailableLocation()` at line 56 in [LocationService.java](./src/main/java/com/lghimfus/vee/services/LocationService.java) is called.
  * The above method retrieves and removes a random available (not used) location from the "world".
  * In order to used the same location multiple times, the code at line 59 `return locs.remove(randomId)` in the above method, should be modified to `return locs.get(randomId)`, hence a location will still remain available after being used to hold an event.
  * We could also add a List<Event> in [Location.java](./src/main/java/com/lghimfus/vee/models/Location.java) to keep track of the events at a certain location. The list would be updated each time a new event is created at the location.
  * **EXTRA**: If we wanted a location to be able to hold multiple events, but at the same time a limited number of events, we could add a static variable to [Location.java](./src/main/java/com/lghimfus/vee/models/Location.java) that would keep track of how many events a location holds. When the maximum number would be reached, the location would be unavailable. That could be done using a boolean variable in [Location.java](./src/main/java/com/lghimfus/vee/models/Location.java).
  
### How would you change your program if you were working with a much larger world size?
  * Rather than generating the whole "world map" beforehand, I would generate locations each time an event requires a new location. In addition to that, I would store locations in a HashSet (which only has uniques entries based on their hash values). The time complexity to add new location would be O(1), therefore total time complexity needed to create the map would be O(eventsNumber) rather than 0(worldSize^2). Some space would also be saved.
  * **OR** I would use the same approach using a database for faster data retrieval.
