## Entities

The following main concepts, "entities", are represented as classes that are **responsible for containing the entity's details, references to associated entities, and derivable information**. Each class is coherent, and allows code sharing for their ID management. The dependencies for an entity's derivable information also matches the entities we expect to find under the original entity (e.g. We expect to be able to get the performances for a given act, and `Act` needs its performances to calculate the report, so it makes sense for `Act` to have a field `performances`).
- *Artist*, an agent representing a person/group with a name that, over time, will put on *acts* under their name.
- *Act* is the main *thing* people want to see. They have a title, duration, associated artist, and can be *performed* at theatres at a particular time.
- *Performance*. A particular performance of an *act* at a particular time, and theatre. They have a given price-per-tier, and at any moment they have their *tickets* being sold and *seats* being taken.
- *Seating*. The current state of all *theatre* seats for a given performance. Some seats are taken. Some are not. Some are premier seats and some are cheap.
- *Theatre*. The venues that *performances* can be held at. They have a floor size and seating size. *Performing* an act requires asking the *theatre* to get a layout of the *seating* so the availability of the seats for a performance, and their prices, can be tracked.
- *Ticket*. The proof that someone deserves a particular *seat* for a *performance*.

## Design considerations

Acts are created via `artist.putAct`, and Performances are created via `act.performAt`, so that the relevant internal states inside `artist` and `act` are updated. However, this leads to a possibility that the constructors are called directly without updating the `artist` or `act`'s internal states. This code smell will have to make do, because the alternative isn't good either: Updating the artist's internal state within the act's constructor implies that `artist` will need to expose a method to add acts, and that can be abused accidentally to result in irrational states).

Note that `Act` provides `getPerformanceIDs()` instead of `getPerformances()`, because:

- `getPerformances` will require a shallow copy of the performance catalogue so the outside-world cannot mess up the internal `_performances` state by e.g. adding a performance to the catalogue that is not part of the act.
- `getPerformanceIDs` hide away the fact that it is backed by a `PerformanceCatalogue`, and hence hiding away the implementation.
The same reasoning applies to other classes that support the `get...IDs()` method.

Other design considerations:
- Classes inherit from `IDableEntity` for code sharing and to enforce immutability of IDs.
- `Catalogue` class and subclasses abstract away the collection of entities with a focused API for this problem domain.
- Each entity (e.g. `Act`) is referenced in multiple places (e.g both `Artist` and server `ActCatalogue`) to simplify search code to what makes most sense in the problem domain.
	- Info desync won't happen as both refs point to same object.
- Classes organised into packages-by-entity, so it's easier to infer domain model from directory structure, and so class dependencies are made explicit via `import` statements.

