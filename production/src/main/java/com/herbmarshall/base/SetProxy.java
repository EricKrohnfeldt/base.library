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
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings( "NullableProblems" )
abstract class SetProxy<E> implements Set<E> {

	private final Set<E> internal;

	SetProxy( Set<E> internal ) {
		this.internal = Objects.requireNonNull( internal );
	}

	@Override
	public int size() {
		return internal.size();
	}

	@Override
	public boolean isEmpty() {
		return internal.isEmpty();
	}

	@Override
	public boolean contains( Object o ) {
		return internal.contains( o );
	}

	@Override
	public Iterator<E> iterator() {
		return internal.iterator();
	}

	@Override
	public Object[] toArray() {
		return internal.toArray();
	}

	@Override
	public <T> T[] toArray( T[] ts ) {
		return internal.toArray( ts );
	}

	@Override
	public boolean add( E e ) {
		return internal.add( e );
	}

	@Override
	public boolean remove( Object o ) {
		return internal.remove( o );
	}

	@Override
	public boolean containsAll( Collection<?> collection ) {
		return internal.containsAll( collection );
	}

	@Override
	public boolean addAll( Collection<? extends E> collection ) {
		return internal.addAll( collection );
	}

	@Override
	public boolean retainAll( Collection<?> collection ) {
		return internal.retainAll( collection );
	}

	@Override
	public boolean removeAll( Collection<?> collection ) {
		return internal.removeAll( collection );
	}

	@Override
	public void clear() {
		internal.clear();
	}

	@Override
	@SuppressWarnings( "EqualsWhichDoesntCheckParameterClass" )
	public boolean equals( Object o ) {
		return internal.equals( o );
	}

	@Override
	public int hashCode() {
		return internal.hashCode();
	}

}
