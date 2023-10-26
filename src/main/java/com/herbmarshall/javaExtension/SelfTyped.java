package com.herbmarshall.javaExtension;

/**
 * A class that contains generic self returning methods.
 * @param <SELF> The same type as {@code this}
 */
public abstract class SelfTyped<SELF extends SelfTyped<SELF>> {

	/**
	 * Internal helper to resolve a self reference.
	 * @return self reference
	 * @throws ClassCastException if {@code SELF} is not equal to the type of {@code this}
	 */
	@SuppressWarnings( "unchecked" )
	protected SELF self() {
		return ( SELF ) this;
	}

}
