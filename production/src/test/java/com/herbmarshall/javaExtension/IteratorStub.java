package com.herbmarshall.javaExtension;

import java.util.Iterator;

class IteratorStub<E> implements Iterator<E> {

	@Override
	public boolean hasNext() {
		throw new UnsupportedOperationException( "Not implemented" );
	}

	@Override
	public E next() {
		throw new UnsupportedOperationException( "Not implemented" );
	}

}
