package com.herbmarshall.javaExtension;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

/** Utils missing from standard library. */
public final class CollectionUtil {

	private CollectionUtil() {}

	/**
	 * Merge the values of two {@link Set} in one.
	 * Will return new {@link Set}
	 * @throws NullPointerException if {@code a} or {@code b} is {@code null}
	 */
	public static <E> Set<E> merge( Set<E> a, Set<E> b ) {
		return Stream.of( Objects.requireNonNull( a ), Objects.requireNonNull( b ) )
			.flatMap( Collection::stream )
			.collect( toUnmodifiableSet() );
	}

	/**
	 * Merge the values of two {@link List} in one.
	 * Will return new {@link List}
	 * @throws NullPointerException if {@code a} or {@code b} is {@code null}
	 */
	public static <E> List<E> merge( List<E> a, List<E> b ) {
		return Stream.of( Objects.requireNonNull( a ), Objects.requireNonNull( b ) )
			.flatMap( Collection::stream )
			.toList();
	}

}
