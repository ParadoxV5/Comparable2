_(May 6<sup>th</sup>, 2021)_ Oh, Java, I can’t believe that we *still* have to write `.compareTo(…) < 0` even in [the latest version, 16](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/lang/Comparable.html)!

----

## Comparable2
**`java.lang.Comparable2`** is a [`Comparable`](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/lang/Comparable.html)-extending interface that adds additional `default` methods to reduce coding repetition.

This extension is written with [Ruby’s `Comparable` module](https://ruby-doc.org/core/Comparable.html)
as the main reference.