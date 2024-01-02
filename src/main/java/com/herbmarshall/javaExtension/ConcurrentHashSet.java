package com.herbmarshall.javaExtension;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link Set} implementation based on {@link ConcurrentHashMap#newKeySet()}.
 * @param <E> The element type
 * @see ConcurrentHashMap#newKeySet()
 * @see ConcurrentHashMap#newKeySet(int)
 */
public final class ConcurrentHashSet<E> extends SetProxy<E> {

	/** Create new instance. */
	public ConcurrentHashSet() {
		super( ConcurrentHashMap.newKeySet() );
	}

	/**
	 * Create new instance will an initial capacity.
	 * @throws IllegalArgumentException if {@code initialCapacity} is negative
	 */
	public ConcurrentHashSet( int initialCapacity ) {
		super( ConcurrentHashMap.newKeySet( initialCapacity ) );
	}

	/**
	 * Create new instance populated with {@code elements}.
	 * @throws NullPointerException if {@code elements} contains any {@code null} elements
	 */
	public ConcurrentHashSet( Collection<? extends E> elements ) {
		this();
		addAll( elements );
	}

}
