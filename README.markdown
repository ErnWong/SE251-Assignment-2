Problem domain:

Theatre Booking System deals with managing the bookings of performances of acts done by certain artists, as well as the ticketing of each performance. The goal is to keep track of these bookings and ticketing via a central system, be able to retrieve these information, and be able to calculate relevant information such as sale reports. The primary motivation of creating this system over older manual solutions is to provide inherent error checking, automation, and speed.

- Performers would like to register themselves as an individual or a group under a shared name as an *Artist*.
- As a registered *Artist*, they can put *Acts* under their name.
- An *Act* has a particular *title*, *duration*, and would usually have useful information associated such as a description, type of act (i.e. is it a play, a keynote, a concert, etc.), suitable audience, and other metadata.
- An *Act* can be performed (a *Performance*) multiple times.
- Each *Performance* is held at a particular *Theatre*, at a particular *time*, with particular *costs* for each tier of seats. There is a possibility that *costs* can change over time especially when there are unoccupied seats close to the sale deadline. However, the *start time* will not make sense to change for a particular *Performance*.
- At any given time, some of the *seats* for a particular performance may be *occupied* after a *ticket* has been issued for some audience for such *seat location*.
- When buying *tickets*, customers and staff will want to know which seats are taken and which are not.
- It is useful for the *Artist* and staff to know how well an *Act* is selling, especially on a per *Performance* basis.
- Part of the system's responsibilities would be to guard against any inconsistent and conflicting booking information. Examples include:
   - Artist names are unique.
   - Seats cannot be double booked.
   - Booking for/under non-existent entities.

Constraints: As a server, all functionality is to be implemented behind, and accessible through, an agreed interface.

Main Concepts:

- *Act*. As the client wants to retrieve the available acts, this concept is important.
- ha
- *Artist*. This is the agent representing a person or a group under witch an Act is put. As a

*Artist*, *Act*, *Performance*, and *Theatre* are all implemented as classes as:
- They provide a logical and coherent unit of encapsulating directly associated information (e.g. Act's title as part of the Act class), and
- Given a breadcrumb to locate a piece of information (e.g. the title of all acts of a given artist name), this representation provide a simple mechanism to retrieve this information that closely matches the intention (i.e. Artists.fromName(name).getPerformances()...)

Problem Context: Assuming same timezone.

Each artist has a name, and can decide to put an act

Goals of client:

Constraints:

Problem Context:
k

Design:

Associations:
- One artist to many acts.
- One act to many performances.
- One performance to many seats.
- Many performance to many theatres.
- One performance to many tickets.

Previously: `TBSList<>` and `TBSNamedList<>`
But now, as `_theatres` need some `init` method, refactoring into `XYZCatalogue`.

Planning to mush everything into same directory, and split out later.

Inheriting from IDableEntity instead of containing a IDGenerator so that immutability is enforced. Using setIDPrefix instead of using classname to avoid unintended consequences (by being explicit about what behaviour to want).

