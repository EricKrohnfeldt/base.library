/*
 * This file is part of herbmarshall.com: base.library  ( hereinafter "base.library" ).
 *
 * base.library is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * base.library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with base.library.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.herbmarshall.base;

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
