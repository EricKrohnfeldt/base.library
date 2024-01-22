package com.herbmarshall.javaExtension;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

	/**
	 * Returns the last element of the given list wrapped in an {@link Optional} object.
	 * If the list is empty, an empty {@link Optional} is returned.
	 * If the last item in the list is {@code null}, then an empty {@link Optional}
	 *
	 * @param list the list from which to retrieve the last element
	 * @param <E> the type of elements in the list
	 * @return an {@link Optional} containing the last element of the list if it is not empty,
	 *         otherwise an empty {@link Optional}
	 */
	public static <E> Optional<E> last( List<E> list ) {
		if ( list.isEmpty() ) return Optional.empty();
		return Optional.ofNullable( list.get( list.size() - 1 ) );
	}

}
