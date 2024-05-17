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

package com.herbmarshall.base.diff;

/** Module to generate a diff visualization of two objects. */
public interface DiffGenerator {

	/**
	 * Create a text representation of an object diff.
	 * <b>Must handle null values for both {@code actual} and {@code expected}</b>
	 * @param expected The value to compare with
	 * @param actual The value to compare to
	 */
	String diff( Object expected, Object actual );

	/**
	 * Quantify the difference between two objects.
	 * Value will be in 1/10000 units.  So 420 = 4.20%
	 * @param expected The expected value to compare with.
	 * @param actual The actual value to compare to.
	 * @return The quantified difference between the expected and actual values.
	 */
	// TODO Remove this default on 2.0 <a href="https://herbmarshall.atlassian.net/browse/UTIL-353">UTIL-353</a>.
	default int quantify( Object expected, Object actual ) {
		// TODO Remove default implementation
		return DiffGeneratorDefault.INSTANCE.quantify( expected, actual );
	}

}
