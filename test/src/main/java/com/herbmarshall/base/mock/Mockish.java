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

package com.herbmarshall.base.mock;

import com.herbmarshall.base.test.JUnitAfterEach;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Primitive mock implementation for use with pre-mock libraries.
 * <p>
 *     What is auto validate?
 *     If turned on the mock will automatically be validated after the test.
 *     If turned off the mock must be validated via {@link Mockish#validate()}.
 * </p>
 */
public abstract class Mockish {

	private static final Set<Mockish> autoInstances = ConcurrentHashMap.newKeySet();

	protected Mockish() {
		this( true );
	}

	protected Mockish( boolean autoValidate ) {
		if ( autoValidate )
			autoInstances.add( this );
	}

	/**
	 * Exposed for testing.
	 * @deprecated Marked as a warning.  Most likely shouldn't be using this.
	 */
	@Deprecated( since = "1.24" )
	@SuppressWarnings( "DeprecatedIsStillUsed" )
	boolean disable() {
		return autoInstances.remove( this );
	}

	/** Execute self validation. */
	public abstract void validate();

	static void executeValidation() {
		autoInstances.forEach( Mockish::validate );
	}

	static {
		JUnitAfterEach.addCallback( Mockish::executeValidation );
	}

}
