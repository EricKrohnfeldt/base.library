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

package com.herbmarshall.base.mock.function;

import com.herbmarshall.base.mock.Mockish;
import org.junit.jupiter.api.Assertions;

/** A {@link Mockish} for {@link Runnable} interface. */
public final class RunnableMockish
	extends Mockish
	implements Runnable {

	private int count = 0;

	/**
	 * Create new instance with {@code autoValidate} turned on.
	 * @see Mockish
	 */
	public RunnableMockish() {
		super();
	}

	/**
	 * Create new instance.
	 * Set {@code autoValidate} on by passing {@code true}, turn off by passing {@code false}.
	 * @see Mockish
	 */
	public RunnableMockish( boolean autovalidate ) {
		super( autovalidate );
	}

	/** Prepare for expected call. */
	public RunnableMockish expect() {
		count++;
		return this;
	}

	@Override
	public void run() {
		if ( --count < 0 )
			throw new IllegalStateException( error_unexpectedCall() );
	}

	// @Override
	/** Validate this mock. */
	public void validate() {
		Assertions.assertEquals(
			0,
			count,
			error_uncalled( count )
		);
	}

	static String error_unexpectedCall() {
		return "Did not expect call to 'run'";
	}

	static String error_uncalled( int count ) {
		return "Did not call 'run' for all expectations: " + count;
	}

}
