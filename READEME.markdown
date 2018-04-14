Previously: `TBSList<>` and `TBSNamedList<>`
But now, as `_theatres` need some `init` method, refactoring into `XYZCatalogue`.

Planning to mush everything into same directory, and split out later.

Inheriting from IDableEntity instead of containing a IDGenerator so that immutability is enforced. Using setIDPrefix instead of using classname to avoid unintended consequences (by being explicit about what behaviour to want).
