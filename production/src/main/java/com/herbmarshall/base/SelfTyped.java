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
