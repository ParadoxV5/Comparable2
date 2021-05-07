package xyz.paradoxv5;
import java.util.Comparator;

/**
  A {@link Comparable}-extending interface that adds
  additional {@code default} methods to reduce coding repetition.
  
  @param <T>
    According to {@link Comparable}, this is implicitly expected to be
    the type which implementing classes can {@link #compareTo(T)},
    which is almost always simply this same type.
    <p>
    Unlike {@link Comparable}, Comparator2 requires {@code T} to implement {@code Comparable2<T>},
    both to match this expectation for one and for {@link #clamp(T, T)}’s typing.
  @implNote
    This extension is written with
    <a href=https://ruby-doc.org/core/Comparable.html>Ruby’s {@code Comparable} module</a>
    as the main reference.
  
  @author ParadoxV5
    <p>
    Released under the <b>MIT License</b>.
    <a href=https://github.com/ParadoxV5/Comparable2>[View on GitHub]</a>
*/
public interface Comparable2<T extends Comparable2<T>> extends java.lang.Comparable<T> {
///

/**
  @param other The {@link Comparable2 T} to compare against
  @return Whether {@link #compareTo(T)} returned negative or not after calling.
  @see #isGreaterThan(T)
  @see #isLessThanOrEqualTo(T)
*/
public default boolean isLessThan(T other) { return compareTo(other) <  0; }
/**
  @param other The {@link Comparable2 T} to compare against
  @return Whether {@link #compareTo(T)} returned positive or not after calling.
  @see #isLessThan(T)
  @see #isGreaterThanOrEqualTo(T)
*/
public default boolean isGreaterThan(T other) { return compareTo(other) >  0; }
/**
  @param other The {@link Comparable2 T} to compare against
  @return Whether {@link #compareTo(T)} returned <em>non-</em>positive or not after calling.
  @see #isGreaterThanOrEqualTo(T)
  @see #isLessThan(T)
*/
public default boolean isLessThanOrEqualTo(T other) { return compareTo(other) <= 0; }
/**
  @param other The {@link Comparable2 T} to compare against
  @return Whether {@link #compareTo(T)} returned <em>non-</em>negative or not after calling.
  @see #isLessThanOrEqualTo(T)
  @see #isGreaterThan(T)
*/
public default boolean isGreaterThanOrEqualTo(T other) { return compareTo(other) >= 0; }

/**
  @param other The {@link Comparable2 T} to compare against
  @return Whether {@link #compareTo(T)} returned {@code 0} or not after calling.
  @see #isNotEqualTo(T)
  
  @apiNote
    This only compares with {@code compareTo(T)}, without {@code ==} nor {@link #equals(Object)}.
    <p>
    See {@link #is(T)} for a {@code default} method that uses all three.
*/
public default boolean isEqualTo(T other) { return compareTo(other) == 0; }
/**
  @param other The {@link Comparable2 T} to compare against
  @return Whether {@link #compareTo(T)} returned <em>non-</em>{@code 0} or not after calling.
  @see #isEqualTo(T)
  
  @apiNote
    This only compares with {@code compareTo(T)}, without {@code !=} nor {@code !}{@link #equals(Object)}.
    <p>
    See {@link #isNot(T)} for a {@code default} method that uses all three.
*/
public default boolean isNotEqualTo(T other) { return compareTo(other) != 0; }
/**
  Calls the following in this given order:<ol>
    <li>{@code ==}
    <li>{@link #equals(Object)}
    <li>{@link #isEqualTo(T)}
  </ol>
  @param other The {@link Comparable2 T} to check against
  @return
    {@code true} if any of the above gives {@code true},
    {@code false} if all of the above gives {@code false}
  @see #isNot(T)
*/
public default boolean is(T other) { return this == other || equals(other) || isEqualTo(other); }
/**
  Calls the following in this given order:<ol>
    <li>{@code !=}
    <li>{@code !}{@link #equals(Object)}
    <li>{@link #isNotEqualTo(T)}
  </ol>
  
  @param other The {@link Comparable2 T} to check against
  @return
    {@code true} if all of the above gives {@code true},
    {@code false} if any of the above gives {@code false}
  @see #is(T)
*/
public default boolean isNot(T other) { return this != other && !equals(other) && isNotEqualTo(other); }

/**
  @param min The minimum (inclusive) bound to compare against
  @param max The maximum (inclusive) bound to compare against
  @return
    {@code true} if {@code this} is <em>not</em> between {@code min} and {@code max} inclusive;
      that is, either {@link #isLessThan(T) isLessThan(min)}
      or {@link #isGreaterThan(T) isGreaterThan(max)};
    {@code false} otherwise.
  @see #isBetween(T, T)
*/
public default boolean isNotBetween(T min, T max) { return isLessThan(min) || isGreaterThan(max); }
/**
  @param min The minimum (inclusive) bound to compare against
  @param max The maximum (inclusive) bound to compare against
  @return
    {@code true} if {@code this} is between {@code min} and {@code max} inclusive;
      that is, both {@link #isGreaterThanOrEqualTo(T) isGreaterThanOrEqualTo(min)}
      and {@link #isLessThanOrEqualTo(T) isLessThanOrEqualTo(max)};
    {@code false} otherwise.
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
public default Comparable2<T> clamp(T min, T max) {
  if(isLessThan(min)) return min;
  if(isGreaterThan(max)) return max;
  return this;
}

/**
  @return
    A <strong>{@code new}</strong> {@link Comparator} whose {@link Comparator#compare(T, T)}
    redirects to {@link #compareTo(T)} of the first {@link Comparable2 T} (“{@code o1}”) with
    the second {@link Comparable2 T} (“{@code o2}”) as its parameter.
  @implSpec
    Because this method generates a <em>{@code new}</em> {@code Comparator},
    multiple calls will generate multiple new {@code Comparator}s which will fail <em>both</em>
    {@code ==} <strong>and {@link Comparator#equals(Object)}</strong> tests.
*/
public static <T extends Comparable2<T>> Comparator<T> generateComparator() { return new Comparator<>() {
  @Override public int compare(T o1, T o2) { return o1.compareTo(o2); }
}; }

///
}