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

import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

/** A mock for {@link Runnable} interface. */
public final class RunnableMockishFailing
	// extends Mockish
	implements Runnable {

	private final Queue<RuntimeException> exceptions = new LinkedList<>();

	/** Prepare for expected call. */
	public RunnableMockishFailing expect( RuntimeException exception ) {
		exceptions.add( Objects.requireNonNull( exception ) );
		return this;
	}

	@Override
	public void run() {
		throw Optional.ofNullable( exceptions.poll() )
			.orElseThrow( () -> new IllegalStateException( error_unexpectedCall() ) );
	}

	// @Override
	/** Validate this mock. */
	public void validate() {
		Assertions.assertTrue(
			exceptions.isEmpty(),
			error_uncalled( exceptions.size() )
		);
	}

	static String error_unexpectedCall() {
		return "Did not expect call to 'run'";
	}

	static String error_uncalled( int count ) {
		return "Did not call 'run' for all expectations: " + count;
	}

}
