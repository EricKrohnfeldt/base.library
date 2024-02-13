package com.herbmarshall.javaExtension;

import java.util.Collection;
import java.util.Iterator;
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
	 * Returns the last element of the given {@link List} wrapped in an {@link Optional} object.
	 * If the {@link List} is empty, an empty {@link Optional} is returned.
	 * If the last item in the {@link List} is {@code null}, then an empty {@link Optional}.
	 * The last item will be calculated using index.
	 *
	 * @param list the {@link List} from which to retrieve the last element
	 * @param <E> the type of elements in the {@link List}
	 * @return an {@link Optional} containing the last element of the {@link List} if it is not empty,
	 *         otherwise an empty {@link Optional}
	 */
	public static <E> Optional<E> last( List<E> list ) {
		if ( list.isEmpty() ) return Optional.empty();
		return Optional.ofNullable( list.get( list.size() - 1 ) );
	}

	/**
	 * Returns the last element of the given {@link Collection} wrapped in an {@link Optional} object.
	 * If the {@link Collection} is empty, an empty {@link Optional} is returned.
	 * If the last item in the {@link Collection} is {@code null}, then an empty {@link Optional}.
	 * The last item will be calculated using an {@link Iterator}.
	 *
	 * @param collection the {@link Collection} from which to retrieve the last element
	 * @param <E> the type of elements in the {@link Collection}
	 * @return an {@link Optional} containing the last element of the {@link Collection} if it is not empty,
	 *         otherwise an empty {@link Optional}
	 * @see Collection#iterator()
	 */
	public static <E> Optional<E> last( Collection<E> collection ) {
		E lastElement = null;
		for ( E element : collection )
			lastElement = element;
		return Optional.ofNullable( lastElement );
	}

}
