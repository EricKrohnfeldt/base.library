package com.herbmarshall.javaExtension;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings( "NullableProblems" )
class SetMock<E> implements Set<E> {

	int size_output;
	boolean size_called;
	boolean isEmpty_output;
	boolean isEmpty_called;
	Object contains_input;
	boolean contains_output;
	boolean contains_called;
	Iterator<E> iterator_output;
	boolean iterator_called;
	Object[] toArray_input;
	Object[] toArray_output;
	boolean toArray_called;
	E add_input;
	boolean add_output;
	boolean add_called;
	Object remove_input;
	boolean remove_output;
	boolean remove_called;
	Collection<?> containsAll_input;
	boolean containsAll_output;
	boolean containsAll_called;
	Collection<? extends E> addAll_input;
	boolean addAll_output;
	boolean addAll_called;
	Collection<?> retainAll_input;
	boolean retainAll_output;
	boolean retainAll_called;
	Collection<?> removeAll_input;
	boolean removeAll_output;
	boolean removeAll_called;
	boolean clear_called;
	Object equals_input;
	boolean equals_output;
	boolean equals_called;
	int hashCode_output;
	boolean hashCode_called;

	@Override
	public int size() {
		size_called = true;
		return size_output;
	}

	@Override
	public boolean isEmpty() {
		isEmpty_called = true;
		return isEmpty_output;
	}

	@Override
	public boolean contains( Object o ) {
		contains_called = true;
		contains_input = o;
		return contains_output;
	}

	@Override
	public Iterator<E> iterator() {
		iterator_called = true;
		return iterator_output;
	}

	@Override
	public Object[] toArray() {
		toArray_called = true;
		return toArray_output;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public <T> T[] toArray( T[] ts ) {
		toArray_called = true;
		toArray_input = ts;
		return ( T[] ) toArray_output;
	}

	@Override
	public boolean add( E e ) {
		add_called = true;
		add_input = e;
		return add_output;
	}

	@Override
	public boolean remove( Object o ) {
		remove_called = true;
		remove_input = o;
		return remove_output;
	}

	@Override
	public boolean containsAll( Collection<?> collection ) {
		containsAll_called = true;
		containsAll_input = collection;
		return containsAll_output;
	}

	@Override
	public boolean addAll( Collection<? extends E> collection ) {
		addAll_called = true;
		addAll_input = collection;
		return addAll_output;
	}

	@Override
	public boolean retainAll( Collection<?> collection ) {
		retainAll_called = true;
		retainAll_input = collection;
		return retainAll_output;
	}

	@Override
	public boolean removeAll( Collection<?> collection ) {
		removeAll_called = true;
		removeAll_input = collection;
		return removeAll_output;
	}

	@Override
	public void clear() {
		clear_called = true;
	}

	@Override
	@SuppressWarnings( "EqualsWhichDoesntCheckParameterClass" )
	public boolean equals( Object o ) {
		equals_called = true;
		equals_input = o;
		return equals_output;
	}

	@Override
	public int hashCode() {
		hashCode_called = true;
		return hashCode_output;
	}

}
