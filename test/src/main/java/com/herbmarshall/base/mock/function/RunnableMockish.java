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

/** A mock for {@link Runnable} interface. */
public final class RunnableMockish
	// extends Mockish
	implements Runnable {

	static final String ERROR_UNCALLED = "Did not call 'accept' for all expectations: %s";

	private int count = 0;

	/** Prepare for expected call. */
	public RunnableMockish expect() {
		count++;
		return this;
	}

	@Override
	public void run() {
		count--;
	}

	void validate() {
		Assertions.assertEquals(
			0,
			count,
			ERROR_UNCALLED.formatted( count )
		);
	}

}
