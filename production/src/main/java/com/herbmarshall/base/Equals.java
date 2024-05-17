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

import java.util.Arrays;
import java.util.Objects;

/** The Equals class provides a method for evaluating if two objects are equal by reference or by value. */
public final class Equals {

	private Equals() {}

	/**
	 * Evaluates if two objects are equal by reference or by value.
	 * @param a the first object to compare
	 * @param b the second object to compare
	 * @return {@code true} if the objects are equal by reference or by value, {@code false} otherwise
	 */
	public static boolean evaluate( Object a, Object b ) {
		return checkEqualsByReference( a, b ) || checkEqualsByValue( a, b );
	}

	private static boolean checkEqualsByReference( Object a, Object b ) {
		return a == b;
	}

	private static boolean checkEqualsByValue( Object a, Object b ) {
		return isArray( a ) && isArray( b ) ?
			deepEquals( a, b ) :
			Objects.equals( a, b );
	}

	private static boolean isArray( Object value ) {
		return value != null && value.getClass().isArray();
	}

	private static boolean deepEquals( Object a, Object b ) {
		return Arrays.deepEquals( ( Object[] ) a, ( Object[] ) b );
	}

}
