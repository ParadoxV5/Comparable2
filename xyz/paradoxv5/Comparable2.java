package xyz.paradoxv5;
import java.util.Comparator;

import java.lang.Comparable;
import java.lang.Object;

/**
A {@link Comparable}-extending interface that adds additional {@code default} methods to reduce coding repetition.

@param <T> <br>
According to {@link Comparable}, this is implicitly expected to be
the type which implementing classes can {@link #compareTo(T)},
usually types those {@code super} said classes.
<br>
Unlike {@link Comparable}, Comparator2 requires {@code T} to implement {@code Comparable<T>} to
match the requirements laid out by Comparable.

@implNote
This extension is written with
<a href=https://ruby-doc.org/core/Comparable.html>Ruby’s {@code Comparable} module</a>
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
  @see #isGreaterThan(T)
  @see #isLessThanOrEqualTo(T)
*/
public default boolean isLessThan(T other)             { return compareTo(other) <  0; }
/** @return Whether {@link #compareTo(T)} returned positive or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isLessThan(T)
  @see #isGreaterThanOrEqualTo(T)
*/
public default boolean isGreaterThan(T other)          { return compareTo(other) >  0; }
/** @return Whether {@link #compareTo(T)} returned non-positive or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isGreaterThanOrEqualTo(T)
  @see #isLessThan(T)
*/
public default boolean isLessThanOrEqualTo(T other)    { return compareTo(other) <= 0; }
/** @return Whether {@link #compareTo(T)} returned non-negative or not after calling.
  @param other The {@link Comparable2 T} to compare against
  @see #isLessThanOrEqualTo(T)
  @see #isGreaterThan(T)
*/
public default boolean isGreaterThanOrEqualTo(T other) { return compareTo(other) >= 0; }

/** @return Whether {@link #compareTo(T)} returned {@code 0} or not after calling.
  
  @apiNote
  This only compares with {@code compareTo(T)}, not {@code ==} nor
  {@link #equals(Object)} (which is not to be confused with).
  <br>
  See {@link #is(T)} for a pre-written method that recognizes all three.
  
  @param other The {@link Comparable2 T} to compare against
  @see #isNotEqualTo(T)
*/
public default boolean isEqualTo(T other) { return compareTo(other) == 0; }
/** @return Whether {@link #compareTo(T)} returned non-{@code 0} or not after calling.
  
  @apiNote
  This only compares with {@code compareTo(T)}, not {@code !=} nor
  {@code !} {@link #equals(Object)} (which is not to be confused with).
  <br>
  See {@link #isNot(T)} for a pre-written method that recognizes all three.
  
  @param other The {@link Comparable2 T} to compare against
  @see #isEqualTo(T)
*/
public default boolean isNotEqualTo(T other) { return compareTo(other) != 0; }
/** Calls the following in this given order:<ol>
  <li>{@code ==}
  <li>{@link #equals(Object)}
  <li>{@link #isEqualTo(T)}
  </ol>
  
  @return
  {@code true} if any of the above gives {@code true},
  {@code false} if all of the above gives {@code false}
  
  @param other The {@link Comparable2 T} to compare against
  @see #isNot(T)
*/
public default boolean is(T other) { return this == other || equals(other) || isEqualTo(other); }
/** Calls the following in this given order:<ol>
  <li>{@code !=}
  <li>{@code !} {@link #equals(Object)}
  <li>{@link #isNotEqualTo(T)}
  </ol>
  
  @return
  {@code true} if all of the above gives {@code true},
  {@code false} if any of the above gives {@code false}
  
  @param other The {@link Comparable2 T} to compare against
  @see #is(T)
*/
public default boolean isNot(T other) { return this != other && !equals(other) && isNotEqualTo(other); }

/** @return
  {@code true} if {@link #isLessThan(T) isLess(min)} or {@link #isGreaterThan(T) isGreater(max)},
  {@code false} otherwise.
  
  @param min The minimum {@link Comparable2 T} bound to compare against
  @param max The maximum {@link Comparable2 T} bound to compare against
  
  @see #isBetween(T, T)
*/
public default boolean isNotBetween(T min, T max) { return isLessThan(min) || isGreaterThan(max); }
/** @return
  {@code true} if {@link #isGreaterThanOrEqualTo(T) isGreaterEqual(min)} or {@link #isLessThanOrEqualTo(T) isLessEqual(max)},
  {@code false} otherwise.
  
  @param min The minimum {@link Comparable2 T} bound to compare against
  @param max The maximum {@link Comparable2 T} bound to compare against
  
  @see #isNotBetween(T, T)
*/
public default boolean isBetween(T min, T max) { return isGreaterThanOrEqualTo(min) && isLessThanOrEqualTo(max); }

/** @return
  {@code min} if {@link #isLessThan(T) isLess(min)},
  {@code max} if {@link #isGreaterThan(T) isGreater(max)},
  {@code this} otherwise.
  
  @param min The minimum {@link Comparable2 T} bound to compare against
  @param max The maximum {@link Comparable2 T} bound to compare against
  
  @see #isNotBetween(T, T)
  @see #isBetween(T, T)
*/
public default Comparable<T> clamp(T min, T max) {
  if(isLessThan(min)) return min;
  if(isGreaterThan(max)) return max;
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
public static <T extends Comparable<T>> Comparator<T> generateComparator() { return new Comparator<>() {
  @Override public int compare(T o1, T o2) { return o1.compareTo(o2); }
}; }

///
}