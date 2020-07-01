package java.lang;
import java.util.Comparator;

/**
A {@link Comparable}-{@code extend}ing interface that adds additional {@code default} methods.

@param <T> <br>
According to {@link Comparable}, this is implicitly expected to be
the type which implementing classes can {@link #compareTo(T)},
usually types those {@code super} said classes.
<br>
Unlike {@link Comparable}, Comparator2 requires {@code T} to implement {@code Comparable<T>} to
match the requirements laid out by Comparable.

@implNote
This extension is written with
<a href=https://ruby-doc.org/core/Comparable.html>Ruby’s Comparable module</a>
as the main reference.

@author ParadoxV5
<p>
Released under the <b>MIT Lisense</b>.
<br>
<a href=https://github.com/ParadoxV5/Comparable2>View on GitHub</a>
*/
public interface Comparable2<T extends Comparable<T>> extends Comparable<T> {
///

/** @return Whether {@link #compareTo(T)} returned negative or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isGreater(T)
  @see #isLessEqual(T)
*/
public default boolean isLess(T other)         { return compareTo(other) <  0; }
/** @return Whether {@link #compareTo(T)} returned positive or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isLess(T)
  @see #isGreaterEqual(T)
*/
public default boolean isGreater(T other)      { return compareTo(other) >  0; }
/** @return Whether {@link #compareTo(T)} returned non-positive or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isGreaterEqual(T)
  @see #isLess(T)
*/
public default boolean isLessEqual(T other)    { return compareTo(other) <= 0; }
/** @return Whether {@link #compareTo(T)} returned non-negative or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isLessEqual(T)
  @see #isGreater(T)
*/
public default boolean isGreaterEqual(T other) { return compareTo(other) >= 0; }


/** @return Whether {@link #compareTo(T)} returned {@code 0} or not after calling.
  
  @apiNote
  This only compares with {@code compareTo(T)}, not {@code ==} nor
  {@link #equals(Object)} (which is not to be confused with).
  <br>
  See {@link #is(T)} for a pre-written method that recognizes all three.
  
  @param other The {@link Comparable2 T} to compare against
  @see #isNotEqual(T)
*/
public default boolean isEqual(T other) { return compareTo(other) == 0; }
/** @return Whether {@link #compareTo(T)} returned non-{@code 0} or not after calling.
  
  @apiNote
  This only compares with {@code compareTo(T)}, not {@code !=} nor
  {@code !} {@link #equals(Object)} (which is not to be confused with).
  <br>
  See {@link #isNot(T)} for a pre-written method that recognizes all three.
  
  @param other The {@link Comparable2 T} to compare against
  @see #isEqual(T)
*/
public default boolean isNotEqual(T other) { return compareTo(other) != 0; }
/** Calls the following in this given order:<ol>
  <li>{@code ==}
  <li>{@link #equals(Object)}
  <li>{@link #isEqual(T)}
  </ol>
  
  @return
  {@code true} if any of the above gives {@code true},
  {@code false} if all of the above gives {@code false}
  
  @param other The {@link Comparable2 T} to compare against
  @see #isNot(T)
*/
public default boolean is(T other) { return this == other || equals(other) || isEqual(other); }
/** Calls the following in this given order:<ol>
  <li>{@code !=}
  <li>{@code !} {@link #equals(Object)}
  <li>{@link #isNotEqual(T)}
  </ol>
  
  @return
  {@code true} if all of the above gives {@code true},
  {@code false} if any of the above gives {@code false}
  
  @param other The {@link Comparable2 T} to compare against
  @see #is(T)
*/
public default boolean isNot(T other) { return this != other && !equals(other) && isNotEqual(other); }

/** @return
  {@code true} if {@link #isLess(T) isLess(min)} or {@link #isGreater(T) isGreater(max)},
  {@code false} otherwise.
  
  @param min The minimum {@link Comparable2 T} bound to compare against
  @param max The maximum {@link Comparable2 T} bound to compare against
  
  @see #isBetween(T, T)
*/
public default boolean isNotBetween(T min, T max) { return isLess(min) || isGreater(max); }
/** @return
  {@code true} if {@link #isGreaterEqual(T) isGreaterEqual(min)} or {@link #isLessEqual(T) isLessEqual(max)},
  {@code false} otherwise.
  
  @param min The minimum {@link Comparable2 T} bound to compare against
  @param max The maximum {@link Comparable2 T} bound to compare against
  
  @see #isNotBetween(T, T)
*/
public default boolean isBetween(T min, T max) { return isGreaterEqual(min) && isLessEqual(max); }

/** @return
  {@code min} if {@link #isLess(T) isLess(min)},
  {@code max} if {@link #isGreater(T) isGreater(max)},
  {@code this} otherwise.
  
  @param min The minimum {@link Comparable2 T} bound to compare against
  @param max The maximum {@link Comparable2 T} bound to compare against
  
  @see #isNotBetween(T, T)
  @see #isBetween(T, T)
*/
public default Comparable<T> clamp(T min, T max) {
  if(isLess(min)) return min;
  if(isGreater(max)) return max;
  return this;
}

/** @return
  A <strong>{@code new}</strong> {@link Comparator} whose {@link Comparator#compare(T, T)}
  redirects to {@link #compareTo(T)} of the first {@link Comparable2 T} (“{@code o1}”) with
  the second {@link Comparable2 T} (“{@code o2}”) as its parameter.
  
  @implSpec
  Note that both {@code defaultComparator() == defaultComparator()} <strong>and
  {@link Comparator#equals(Object) defaultComparator().equals(defaultComparator())}
  (which isn’t {@link Override implemented}) </strong> evaluates to <u>{@code false}</u>.
*/
public static <T extends Comparable<T>> Comparator<T> defaultComparator() { return new Comparator<T>() {
  @Override public int compare(T o1, T o2) { return o1.compareTo(o2); }
}; }

///
}